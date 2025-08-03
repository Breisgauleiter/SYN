import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { User, AuthTokens, LoginCredentials, RegisterData } from '@/types/auth'
import { authService } from '@/services/authService'

export const useAuthStore = defineStore('auth', () => {
  // Sacred State
  const user = ref<User | null>(null)
  const tokens = ref<AuthTokens | null>(null)
  const isLoading = ref(false)
  const error = ref<string | null>(null)

  // Sacred Computed
  const isAuthenticated = computed(() => !!user.value && !!tokens.value)
  const userProfile = computed(() => user.value)
  const hasGeneKeysProfile = computed(() => 
    user.value?.geneKeysProfile && 
    user.value.geneKeysProfile.activationSequence
  )

  // Sacred Actions
  async function login(credentials: LoginCredentials) {
    isLoading.value = true
    error.value = null
    
    try {
      const response = await authService.login(credentials)
      user.value = response.user
      tokens.value = response.tokens
      
      // Store tokens in localStorage for persistence
      localStorage.setItem('syntopia_tokens', JSON.stringify(response.tokens))
      localStorage.setItem('syntopia_user', JSON.stringify(response.user))
      
      console.log('🌟 Sacred Login Successful:', user.value?.username)
      return response
    } catch (err: any) {
      error.value = err.message || 'Login failed'
      console.error('🔴 Sacred Login Error:', err)
      throw err
    } finally {
      isLoading.value = false
    }
  }

  async function register(registerData: RegisterData) {
    isLoading.value = true
    error.value = null
    
    try {
      const response = await authService.register(registerData)
      user.value = response.user
      tokens.value = response.tokens
      
      // Store tokens in localStorage
      localStorage.setItem('syntopia_tokens', JSON.stringify(response.tokens))
      localStorage.setItem('syntopia_user', JSON.stringify(response.user))
      
      console.log('🌟 Sacred Registration Successful:', user.value?.username)
      return response
    } catch (err: any) {
      error.value = err.message || 'Registration failed'
      console.error('🔴 Sacred Registration Error:', err)
      throw err
    } finally {
      isLoading.value = false
    }
  }

  async function logout() {
    try {
      if (tokens.value) {
        await authService.logout(tokens.value.accessToken)
      }
    } catch (err) {
      console.warn('🟡 Logout API call failed, proceeding with local logout')
    }
    
    // Clear local state
    user.value = null
    tokens.value = null
    error.value = null
    
    // Clear localStorage
    localStorage.removeItem('syntopia_tokens')
    localStorage.removeItem('syntopia_user')
    
    console.log('🌙 Sacred Logout Completed')
  }

  async function refreshToken() {
    if (!tokens.value?.refreshToken) return false
    
    try {
      const newTokens = await authService.refreshToken(tokens.value.refreshToken)
      tokens.value = newTokens
      
      localStorage.setItem('syntopia_tokens', JSON.stringify(newTokens))
      return true
    } catch (err) {
      console.error('🔴 Token Refresh Failed:', err)
      await logout() // Force logout on refresh failure
      return false
    }
  }

  async function checkAuthStatus() {
    const storedTokens = localStorage.getItem('syntopia_tokens')
    const storedUser = localStorage.getItem('syntopia_user')
    
    if (storedTokens && storedUser) {
      try {
        tokens.value = JSON.parse(storedTokens)
        user.value = JSON.parse(storedUser)
        
        // Verify token is still valid
        const isValid = await authService.verifyToken(tokens.value!.accessToken)
        if (!isValid) {
          const refreshed = await refreshToken()
          if (!refreshed) {
            await logout()
          }
        }
      } catch (err) {
        console.error('🔴 Auth Status Check Failed:', err)
        await logout()
      }
    }
  }

  async function updateProfile(profileData: Partial<User>) {
    if (!user.value) return
    
    isLoading.value = true
    error.value = null
    
    try {
      const updatedUser = await authService.updateProfile(profileData)
      user.value = { ...user.value, ...updatedUser }
      
      localStorage.setItem('syntopia_user', JSON.stringify(user.value))
      
      console.log('🌟 Sacred Profile Updated')
      return user.value
    } catch (err: any) {
      error.value = err.message || 'Profile update failed'
      console.error('🔴 Profile Update Error:', err)
      throw err
    } finally {
      isLoading.value = false
    }
  }

  function clearError() {
    error.value = null
  }

  return {
    // State
    user,
    tokens,
    isLoading,
    error,
    
    // Getters
    isAuthenticated,
    userProfile,
    hasGeneKeysProfile,
    
    // Actions
    login,
    register,
    logout,
    refreshToken,
    checkAuthStatus,
    updateProfile,
    clearError
  }
})
