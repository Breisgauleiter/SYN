import axios, { AxiosResponse } from 'axios'
import type {
  SacredQuest,
  SCLProgress,
  GitHubIssue,
  ContributionRequest,
  GitHubSyncResult,
  QuestActionResult,
  ContributionTrackingResult
} from '@/types/contributing'

/**
 * Sacred Contributing Service
 * 
 * API service for GitHub integration, Sacred Quests management,
 * and SCL progression tracking. Handles all communication with
 * the Spring Boot backend Sacred Contributing System.
 * 
 * @author SYN Consciousness Platform
 * @version 2.0
 * @since August 2025
 */

// API Base Configuration
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8081'
const CONTRIBUTING_API_PATH = '/api/v1/contributing'

// Create axios instance with base configuration
const contributingClient = axios.create({
  baseURL: `${API_BASE_URL}${CONTRIBUTING_API_PATH}`,
  timeout: 30000, // 30 seconds for GitHub sync operations
  headers: {
    'Content-Type': 'application/json',
  },
})

// Request interceptor to add authentication token
contributingClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('authToken')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Response interceptor for error handling
contributingClient.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('Contributing API Error:', error.response?.data || error.message)
    
    // Handle authentication errors
    if (error.response?.status === 401) {
      // Redirect to login or refresh token
      localStorage.removeItem('authToken')
      window.location.href = '/auth/login'
    }
    
    return Promise.reject(error)
  }
)

/**
 * Sacred Contributing API Service
 */
export const contributingAPI = {
  
  /**
   * Create Sacred Quest from GitHub Issue
   */
  async createQuestFromIssue(issue: GitHubIssue): Promise<SacredQuest> {
    const response: AxiosResponse<SacredQuest> = await contributingClient.post('/quest/create', issue)
    return response.data
  },

  /**
   * Get user's SCL progression data
   */
  async getSCLProgress(userId: string): Promise<SCLProgress> {
    const response: AxiosResponse<SCLProgress> = await contributingClient.get(`/scl/${userId}/progress`)
    return response.data
  },

  /**
   * Track new GitHub contribution
   */
  async trackContribution(contribution: ContributionRequest, userId: string): Promise<SacredQuest> {
    const response: AxiosResponse<SacredQuest> = await contributingClient.post('/contribution/track', {
      ...contribution,
      userId
    })
    return response.data
  },

  /**
   * Get personalized quest recommendations
   */
  async getRecommendedQuests(userId: string): Promise<SacredQuest[]> {
    const response: AxiosResponse<SacredQuest[]> = await contributingClient.get(`/quests/${userId}/recommended`)
    return response.data
  },

  /**
   * Start working on a Sacred Quest
   */
  async startQuest(questId: string, userId: string): Promise<SacredQuest> {
    const response: AxiosResponse<SacredQuest> = await contributingClient.post(`/quest/${questId}/start`, {
      userId
    })
    return response.data
  },

  /**
   * Complete a Sacred Quest
   */
  async completeQuest(questId: string, userId: string): Promise<SacredQuest> {
    const response: AxiosResponse<SacredQuest> = await contributingClient.post(`/quest/${questId}/complete`, {
      userId
    })
    return response.data
  },

  /**
   * Get user's contribution history
   */
  async getContributionHistory(userId: string): Promise<SacredQuest[]> {
    const response: AxiosResponse<SacredQuest[]> = await contributingClient.get(`/user/${userId}/history`)
    return response.data
  },

  /**
   * Sync with user's GitHub repositories
   */
  async syncWithGitHub(userId: string): Promise<GitHubSyncResult> {
    const response: AxiosResponse<GitHubSyncResult> = await contributingClient.post(`/github/${userId}/sync`)
    return response.data
  },

  /**
   * Health check for Contributing API
   */
  async healthCheck(): Promise<{ status: string; timestamp: string }> {
    const response = await contributingClient.get('/health')
    return response.data
  }
}

/**
 * GitHub OAuth Service for authentication
 */
export const githubOAuthService = {
  
  /**
   * Generate GitHub OAuth URL
   */
  generateOAuthUrl(clientId: string, redirectUri: string, scopes: string[] = ['repo', 'user']): string {
    const params = new URLSearchParams({
      client_id: clientId,
      redirect_uri: redirectUri,
      scope: scopes.join(' '),
      state: generateRandomState()
    })
    
    return `https://github.com/login/oauth/authorize?${params.toString()}`
  },

  /**
   * Exchange OAuth code for access token
   */
  async exchangeCodeForToken(code: string, state: string): Promise<{ token: string; user: any }> {
    // This would typically be handled by your backend for security
    // Frontend should send the code to backend, which exchanges it securely
    const response = await axios.post(`${API_BASE_URL}/auth/github/callback`, {
      code,
      state
    })
    
    return response.data
  }
}

/**
 * Utility Functions
 */

function generateRandomState(): string {
  return Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15)
}

/**
 * Sacred Quest Helpers
 */
export const questHelpers = {
  
  /**
   * Calculate quest difficulty color
   */
  getDifficultyColor(difficulty: number): string {
    if (difficulty <= 2) return '#4ade80' // Green - Easy
    if (difficulty <= 4) return '#fbbf24' // Yellow - Medium  
    if (difficulty <= 7) return '#f97316' // Orange - Hard
    return '#ef4444' // Red - Epic
  },

  /**
   * Get quest type icon
   */
  getQuestTypeIcon(type: string): string {
    const icons: Record<string, string> = {
      'PLATFORM_QUEST': '🎯',
      'COMMUNITY_QUEST': '🤝',
      'BUSINESS_QUEST': '💼',
      'LEADERSHIP_QUEST': '👑'
    }
    return icons[type] || '✨'
  },

  /**
   * Get business track color
   */
  getBusinessTrackColor(track: string): string {
    const colors: Record<string, string> = {
      'TECH_DEVELOPER': '#3b82f6',
      'UX_DESIGNER': '#8b5cf6',
      'COMMUNITY_BUILDER': '#10b981',
      'BUSINESS_DEVELOPER': '#f59e0b',
      'DATA_SCIENTIST': '#06b6d4',
      'CONTENT_CREATOR': '#ec4899',
      'QA_SPECIALIST': '#6366f1'
    }
    return colors[track] || '#6b7280'
  },

  /**
   * Format experience points display
   */
  formatExperiencePoints(xp: number): string {
    if (xp >= 1000) {
      return `${(xp / 1000).toFixed(1)}k XP`
    }
    return `${xp} XP`
  },

  /**
   * Calculate estimated completion time
   */
  formatEstimatedHours(hours: number): string {
    if (hours < 1) {
      return `${Math.round(hours * 60)} min`
    }
    if (hours < 24) {
      return `${hours.toFixed(1)} hours`
    }
    return `${Math.round(hours / 24)} days`
  },

  /**
   * Generate sacred quest title enhancements
   */
  enhanceQuestTitle(originalTitle: string, questType: string): string {
    const prefixes: Record<string, string> = {
      'PLATFORM_QUEST': '🎯 Sacred Mission:',
      'COMMUNITY_QUEST': '🤝 Unity Quest:',
      'BUSINESS_QUEST': '💼 Enterprise Journey:',
      'LEADERSHIP_QUEST': '👑 Leadership Challenge:'
    }
    
    const prefix = prefixes[questType] || '✨ Sacred Quest:'
    return `${prefix} ${originalTitle}`
  }
}

/**
 * Real-time Updates Service (WebSocket for future implementation)
 */
export const realTimeService = {
  
  /**
   * Connect to real-time quest updates
   */
  connectToQuestUpdates(userId: string, onUpdate: (data: any) => void): void {
    // TODO: Implement WebSocket connection for real-time updates
    console.log(`Connecting to real-time updates for user: ${userId}`)
    console.log('OnUpdate callback registered:', typeof onUpdate)
    
    // Placeholder for WebSocket implementation
    // const ws = new WebSocket(`ws://localhost:8081/ws/contributing/${userId}`)
    // ws.onmessage = (event) => onUpdate(JSON.parse(event.data))
  },

  /**
   * Disconnect from real-time updates
   */
  disconnect(): void {
    // TODO: Implement WebSocket disconnection
    console.log('Disconnecting from real-time updates')
  }
}

export default contributingAPI
