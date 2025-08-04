import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { contributingAPI } from '@/services/contributingService'
import type { 
  SacredQuest, 
  SCLProgress, 
  GitHubIssue, 
  ContributionRequest,
  QuestType,
  BusinessTrack,
  ContributionStatus 
} from '@/types/contributing'

/**
 * Sacred Contributing Store
 * 
 * Manages GitHub integration, Sacred Quests, and SCL progression
 * for consciousness-based development through gamified contributions.
 * 
 * Features:
 * - GitHub Issues ↔ Sacred Quests synchronization
 * - SCL (Sacred Consciousness Level) progression tracking
 * - Business Track specialization management
 * - Real-time quest completion detection
 * - Sacred achievement system
 * 
 * @author SYN Consciousness Platform
 * @version 2.0
 * @since August 2025
 */
export const useContributingStore = defineStore('contributing', () => {
  // State
  const userQuests = ref<SacredQuest[]>([])
  const recommendedQuests = ref<SacredQuest[]>([])
  const sclProgress = ref<SCLProgress | null>(null)
  const activeQuest = ref<SacredQuest | null>(null)
  const githubToken = ref<string | null>(null)
  const isGitHubConnected = ref<boolean>(false)
  const contributionHistory = ref<SacredQuest[]>([])
  const loading = ref<boolean>(false)
  const error = ref<string | null>(null)

  // Computed
  const currentSCLLevel = computed(() => sclProgress.value?.currentSCLLevel || 1)
  const currentSCLName = computed(() => sclProgress.value?.currentSCLName || 'Sacred Seeker')
  const nextLevelProgress = computed(() => sclProgress.value?.progressToNextLevel || 0)
  const totalQuestsCompleted = computed(() => sclProgress.value?.totalQuestsCompleted || 0)
  const currentExperiencePoints = computed(() => sclProgress.value?.currentExperiencePoints || 0)
  
  const availableQuests = computed(() => 
    userQuests.value.filter((quest: SacredQuest) => quest.status === 'PROPOSED')
  )
  
  const activeQuests = computed(() => 
    userQuests.value.filter((quest: SacredQuest) => quest.status === 'IN_PROGRESS')
  )
  
  const completedQuests = computed(() => 
    userQuests.value.filter((quest: SacredQuest) => quest.status === 'COMPLETED')
  )

  // Actions
  
  /**
   * Initialize contributing system for current user
   */
  async function initializeContributing(userId: string) {
    try {
      loading.value = true
      error.value = null
      
      // Load user's SCL progress
      await loadSCLProgress(userId)
      
      // Load user's quests
      await loadUserQuests(userId)
      
      // Load recommended quests
      await loadRecommendedQuests(userId)
      
      // Load contribution history
      await loadContributionHistory(userId)
      
      // Check GitHub connection status
      await checkGitHubConnection()
      
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Failed to initialize contributing system'
      console.error('Contributing initialization error:', err)
    } finally {
      loading.value = false
    }
  }

  /**
   * Connect user's GitHub account for quest synchronization
   */
  async function connectGitHub(token: string, userId: string) {
    try {
      loading.value = true
      error.value = null
      
      githubToken.value = token
      
      // Trigger GitHub synchronization
      const syncResult = await contributingAPI.syncWithGitHub(userId)
      
      if (syncResult.success) {
        isGitHubConnected.value = true
        
        // Reload quests after sync
        await loadUserQuests(userId)
        await loadRecommendedQuests(userId)
      }
      
      return syncResult
      
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'GitHub connection failed'
      throw err
    } finally {
      loading.value = false
    }
  }

  /**
   * Transform GitHub Issue into Sacred Quest
   */
  async function createQuestFromIssue(issue: GitHubIssue) {
    try {
      loading.value = true
      error.value = null
      
      const quest = await contributingAPI.createQuestFromIssue(issue)
      
      // Add to user's quests
      userQuests.value.unshift(quest)
      
      return quest
      
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Failed to create quest from issue'
      throw err
    } finally {
      loading.value = false
    }
  }

  /**
   * Start working on a Sacred Quest
   */
  async function startQuest(questId: string, userId: string) {
    try {
      loading.value = true
      error.value = null
      
      const updatedQuest = await contributingAPI.startQuest(questId, userId)
      
      // Update quest in store
      const questIndex = userQuests.value.findIndex((q: SacredQuest) => q.id === questId)
      if (questIndex !== -1) {
        userQuests.value[questIndex] = updatedQuest
      }
      
      activeQuest.value = updatedQuest
      
      return updatedQuest
      
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Failed to start quest'
      throw err
    } finally {
      loading.value = false
    }
  }

  /**
   * Complete a Sacred Quest and update SCL progression
   */
  async function completeQuest(questId: string, userId: string) {
    try {
      loading.value = true
      error.value = null
      
      const completedQuest = await contributingAPI.completeQuest(questId, userId)
      
      // Update quest in store
      const questIndex = userQuests.value.findIndex((q: SacredQuest) => q.id === questId)
      if (questIndex !== -1) {
        userQuests.value[questIndex] = completedQuest
      }
      
      // Reload SCL progress to reflect completion
      await loadSCLProgress(userId)
      
      // Clear active quest if it was the completed one
      if (activeQuest.value?.id === questId) {
        activeQuest.value = null
      }
      
      return completedQuest
      
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Failed to complete quest'
      throw err
    } finally {
      loading.value = false
    }
  }

  /**
   * Track a new GitHub contribution
   */
  async function trackContribution(contribution: ContributionRequest, userId: string) {
    try {
      loading.value = true
      error.value = null
      
      const trackedContribution = await contributingAPI.trackContribution(contribution, userId)
      
      // Add to user's quests
      userQuests.value.unshift(trackedContribution)
      
      // Reload SCL progress
      await loadSCLProgress(userId)
      
      return trackedContribution
      
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Failed to track contribution'
      throw err
    } finally {
      loading.value = false
    }
  }

  /**
   * Load user's SCL progression data
   */
  async function loadSCLProgress(userId: string) {
    try {
      const progress = await contributingAPI.getSCLProgress(userId)
      sclProgress.value = progress
      return progress
    } catch (err) {
      console.error('Failed to load SCL progress:', err)
      throw err
    }
  }

  /**
   * Load user's quests
   */
  async function loadUserQuests(userId: string) {
    try {
      const history = await contributingAPI.getContributionHistory(userId)
      userQuests.value = history
      return history
    } catch (err) {
      console.error('Failed to load user quests:', err)
      throw err
    }
  }

  /**
   * Load personalized quest recommendations
   */
  async function loadRecommendedQuests(userId: string) {
    try {
      const recommended = await contributingAPI.getRecommendedQuests(userId)
      recommendedQuests.value = recommended
      return recommended
    } catch (err) {
      console.error('Failed to load recommended quests:', err)
      throw err
    }
  }

  /**
   * Load user's contribution history
   */
  async function loadContributionHistory(userId: string) {
    try {
      const history = await contributingAPI.getContributionHistory(userId)
      contributionHistory.value = history
      return history
    } catch (err) {
      console.error('Failed to load contribution history:', err)
      throw err
    }
  }

  /**
   * Check if user's GitHub account is connected
   */
  async function checkGitHubConnection() {
    try {
      // TODO: Implement GitHub connection check
      // For now, assume connected if token exists
      isGitHubConnected.value = !!githubToken.value
      return isGitHubConnected.value
    } catch (err) {
      console.error('Failed to check GitHub connection:', err)
      return false
    }
  }

  /**
   * Refresh all contributing data
   */
  async function refreshContributingData(userId: string) {
    await initializeContributing(userId)
  }

  /**
   * Clear contributing store data
   */
  function clearContributingData() {
    userQuests.value = []
    recommendedQuests.value = []
    sclProgress.value = null
    activeQuest.value = null
    githubToken.value = null
    isGitHubConnected.value = false
    contributionHistory.value = []
    loading.value = false
    error.value = null
  }

  /**
   * Get quest by ID
   */
  function getQuestById(questId: string): SacredQuest | undefined {
    return userQuests.value.find((quest: SacredQuest) => quest.id === questId)
  }

  /**
   * Filter quests by type
   */
  function getQuestsByType(type: QuestType): SacredQuest[] {
    return userQuests.value.filter((quest: SacredQuest) => quest.questType === type)
  }

  /**
   * Filter quests by business track
   */
  function getQuestsByBusinessTrack(track: BusinessTrack): SacredQuest[] {
    return userQuests.value.filter((quest: SacredQuest) => quest.businessTrack === track)
  }

  /**
   * Get quests by status
   */
  function getQuestsByStatus(status: ContributionStatus): SacredQuest[] {
    return userQuests.value.filter((quest: SacredQuest) => quest.status === status)
  }

  return {
    // State
    userQuests,
    recommendedQuests,
    sclProgress,
    activeQuest,
    githubToken,
    isGitHubConnected,
    contributionHistory,
    loading,
    error,

    // Computed
    currentSCLLevel,
    currentSCLName,
    nextLevelProgress,
    totalQuestsCompleted,
    currentExperiencePoints,
    availableQuests,
    activeQuests,
    completedQuests,

    // Actions
    initializeContributing,
    connectGitHub,
    createQuestFromIssue,
    startQuest,
    completeQuest,
    trackContribution,
    loadSCLProgress,
    loadUserQuests,
    loadRecommendedQuests,
    loadContributionHistory,
    checkGitHubConnection,
    refreshContributingData,
    clearContributingData,
    getQuestById,
    getQuestsByType,
    getQuestsByBusinessTrack,
    getQuestsByStatus
  }
})
