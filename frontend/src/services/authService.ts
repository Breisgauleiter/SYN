// Sacred Consciousness Services

import axios, { type AxiosResponse } from 'axios'
import type { 
  User, 
  AuthTokens, 
  LoginCredentials, 
  RegisterData, 
  AuthResponse,
  ProfileUpdateData 
} from '@/types/auth'

// Sacred API Configuration
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8081/api/v1'

// Create Sacred Axios Instance
const sacredApi = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
    'X-Sacred-Platform': 'SYNtopia-2.0'
  }
})

// Sacred Request Interceptor
sacredApi.interceptors.request.use(
  (config) => {
    const tokens = localStorage.getItem('syntopia_tokens')
    if (tokens) {
      const parsed = JSON.parse(tokens)
      config.headers.Authorization = `Bearer ${parsed.accessToken}`
    }
    
    // Add consciousness tracking headers
    config.headers['X-Consciousness-Level'] = localStorage.getItem('syntopia_consciousness_level') || 'AWAKENING'
    config.headers['X-Sacred-Timestamp'] = new Date().toISOString()
    
    return config
  },
  (error) => Promise.reject(error)
)

// Sacred Response Interceptor
sacredApi.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config
    
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true
      
      try {
        const tokens = localStorage.getItem('syntopia_tokens')
        if (tokens) {
          const parsed = JSON.parse(tokens)
          const newTokens = await refreshToken(parsed.refreshToken)
          
          localStorage.setItem('syntopia_tokens', JSON.stringify(newTokens))
          originalRequest.headers.Authorization = `Bearer ${newTokens.accessToken}`
          
          return sacredApi(originalRequest)
        }
      } catch (refreshError) {
        // Refresh failed, redirect to login
        localStorage.removeItem('syntopia_tokens')
        localStorage.removeItem('syntopia_user')
        window.location.href = '/auth/login'
      }
    }
    
    return Promise.reject(error)
  }
)

// Sacred Authentication Service
export const authService = {
  async login(credentials: LoginCredentials): Promise<AuthResponse> {
    try {
      const response: AxiosResponse<AuthResponse> = await sacredApi.post('/auth/login', credentials)
      console.log('🌟 Sacred Login API Success')
      return response.data
    } catch (error: any) {
      console.error('🔴 Sacred Login API Error:', error.response?.data || error.message)
      throw new Error(error.response?.data?.message || 'Login failed')
    }
  },

  async register(registerData: RegisterData): Promise<AuthResponse> {
    try {
      const response: AxiosResponse<AuthResponse> = await sacredApi.post('/auth/register', registerData)
      console.log('🌟 Sacred Registration API Success')
      return response.data
    } catch (error: any) {
      console.error('🔴 Sacred Registration API Error:', error.response?.data || error.message)
      throw new Error(error.response?.data?.message || 'Registration failed')
    }
  },

  async logout(accessToken: string): Promise<void> {
    try {
      await sacredApi.post('/auth/logout', {}, {
        headers: { Authorization: `Bearer ${accessToken}` }
      })
      console.log('🌙 Sacred Logout API Success')
    } catch (error: any) {
      console.warn('🟡 Sacred Logout API Warning:', error.response?.data || error.message)
      // Don't throw error for logout - proceed with local cleanup
    }
  },

  async refreshToken(refreshToken: string): Promise<AuthTokens> {
    try {
      const response: AxiosResponse<{ tokens: AuthTokens }> = await sacredApi.post('/auth/refresh', {
        refreshToken
      })
      console.log('🔄 Sacred Token Refresh Success')
      return response.data.tokens
    } catch (error: any) {
      console.error('🔴 Sacred Token Refresh Error:', error.response?.data || error.message)
      throw new Error('Token refresh failed')
    }
  },

  async verifyToken(accessToken: string): Promise<boolean> {
    try {
      await sacredApi.get('/auth/verify', {
        headers: { Authorization: `Bearer ${accessToken}` }
      })
      return true
    } catch (error) {
      return false
    }
  },

  async updateProfile(profileData: ProfileUpdateData): Promise<User> {
    try {
      const response: AxiosResponse<{ user: User }> = await sacredApi.put('/auth/profile', profileData)
      console.log('🌟 Sacred Profile Update Success')
      return response.data.user
    } catch (error: any) {
      console.error('🔴 Sacred Profile Update Error:', error.response?.data || error.message)
      throw new Error(error.response?.data?.message || 'Profile update failed')
    }
  },

  async requestPasswordReset(email: string): Promise<void> {
    try {
      await sacredApi.post('/auth/password-reset-request', { email })
      console.log('🔑 Sacred Password Reset Request Success')
    } catch (error: any) {
      console.error('🔴 Sacred Password Reset Request Error:', error.response?.data || error.message)
      throw new Error(error.response?.data?.message || 'Password reset request failed')
    }
  },

  async resetPassword(token: string, newPassword: string): Promise<void> {
    try {
      await sacredApi.post('/auth/password-reset', { token, newPassword })
      console.log('🔑 Sacred Password Reset Success')
    } catch (error: any) {
      console.error('🔴 Sacred Password Reset Error:', error.response?.data || error.message)
      throw new Error(error.response?.data?.message || 'Password reset failed')
    }
  }
}

// Export sacred refresh token function for interceptor
export const refreshToken = authService.refreshToken
