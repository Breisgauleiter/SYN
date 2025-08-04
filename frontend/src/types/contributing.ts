/**
 * Sacred Contributing System Types
 * 
 * TypeScript definitions for GitHub integration, Sacred Quests,
 * and consciousness-based development tracking.
 * 
 * @author SYN Consciousness Platform
 * @version 2.0
 * @since August 2025
 */

// Enums
export enum QuestType {
  PLATFORM_QUEST = 'PLATFORM_QUEST',
  COMMUNITY_QUEST = 'COMMUNITY_QUEST',
  BUSINESS_QUEST = 'BUSINESS_QUEST',
  LEADERSHIP_QUEST = 'LEADERSHIP_QUEST'
}

export enum BusinessTrack {
  TECH_DEVELOPER = 'TECH_DEVELOPER',
  UX_DESIGNER = 'UX_DESIGNER',
  COMMUNITY_BUILDER = 'COMMUNITY_BUILDER',
  BUSINESS_DEVELOPER = 'BUSINESS_DEVELOPER',
  DATA_SCIENTIST = 'DATA_SCIENTIST',
  CONTENT_CREATOR = 'CONTENT_CREATOR',
  QA_SPECIALIST = 'QA_SPECIALIST'
}

export enum ContributionStatus {
  PROPOSED = 'PROPOSED',
  IN_PROGRESS = 'IN_PROGRESS',
  COMPLETED = 'COMPLETED',
  CANCELLED = 'CANCELLED'
}

// Core Interfaces

export interface SacredQuest {
  id: string
  title: string
  description: string
  questType: QuestType
  businessTrack: BusinessTrack
  status: ContributionStatus
  difficultyLevel: number
  experiencePoints: number
  estimatedHours: number
  userId: string
  githubRepository?: string
  githubIssueNumber?: number
  githubUrl?: string
  githubCommitHash?: string
  githubPullRequestUrl?: string
  createdAt: string
  startedAt?: string
  completedAt?: string
  actualHours?: number
  isLinkedToGitHub: boolean
}

export interface SCLProgress {
  currentSCLLevel: number
  currentSCLName: string
  currentSCLDescription: string
  currentExperiencePoints: number
  nextLevelRequiredXP: number
  progressToNextLevel: number
  totalQuestsCompleted: number
  questsCompletedThisMonth: number
  primaryBusinessTrack: BusinessTrack
  trackLevels: Record<BusinessTrack, number>
  trackXP: Record<BusinessTrack, number>
  completedSkills: string[]
  recommendedNextSkills: string[]
  consciousnessGrowthRate: number
  consciousnessEvolutionTrend: string
  nextLevelRequirements: string[]
  questsRequiredForNextLevel: number
}

export interface GitHubIssue {
  id: number
  title: string
  description: string
  repository: string
  issueNumber: number
  githubUrl: string
  labels: string[]
  assignees: string[]
  state: 'open' | 'closed'
  difficulty?: string
  estimatedHours?: number
  createdAt: string
  updatedAt: string
}

export interface ContributionRequest {
  title: string
  description: string
  questType: QuestType
  businessTrack: BusinessTrack
  difficultyLevel: number
  estimatedHours: number
  githubRepository?: string
  githubIssueNumber?: number
  githubUrl?: string
  githubCommitHash?: string
  githubPullRequestUrl?: string
}

// API Response Types

export interface GitHubSyncResult {
  success: boolean
  message: string
  questsCreated: number
  questsUpdated: number
  errors: string[]
}

export interface QuestActionResult {
  success: boolean
  quest: SacredQuest
  message: string
  xpAwarded?: number
  levelUpAchieved?: boolean
}

export interface ContributionTrackingResult {
  success: boolean
  contribution: SacredQuest
  message: string
  sclProgressUpdated: boolean
  newAchievements: string[]
}

// Store State Types

export interface ContributingState {
  userQuests: SacredQuest[]
  recommendedQuests: SacredQuest[]
  sclProgress: SCLProgress | null
  activeQuest: SacredQuest | null
  githubToken: string | null
  isGitHubConnected: boolean
  contributionHistory: SacredQuest[]
  loading: boolean
  error: string | null
}

// Utility Types

export interface QuestFilter {
  questType?: QuestType
  businessTrack?: BusinessTrack
  status?: ContributionStatus
  difficultyLevel?: number
  linkedToGitHub?: boolean
}

export interface QuestSearchCriteria extends QuestFilter {
  searchTerm?: string
  sortBy?: 'createdAt' | 'experiencePoints' | 'difficultyLevel' | 'estimatedHours'
  sortOrder?: 'asc' | 'desc'
  limit?: number
  offset?: number
}

export interface SCLLevelInfo {
  level: number
  name: string
  description: string
  requiredXP: number
  unlocked: boolean
  requirements: string[]
}

export interface BusinessTrackProgress {
  track: BusinessTrack
  level: number
  currentXP: number
  nextLevelXP: number
  completedQuests: number
  averageQuestDifficulty: number
  specializations: string[]
}

// GitHub Integration Types

export interface GitHubRepository {
  id: number
  name: string
  fullName: string
  description: string
  url: string
  language: string
  stars: number
  forks: number
  isPrivate: boolean
  hasIssues: boolean
}

export interface GitHubUser {
  id: number
  login: string
  name: string
  email: string
  avatarUrl: string
  repositories: GitHubRepository[]
  totalContributions: number
}

export interface GitHubOAuthConfig {
  clientId: string
  redirectUri: string
  scopes: string[]
}

// Sacred Achievement System

export interface SacredAchievement {
  id: string
  title: string
  description: string
  iconUrl: string
  category: 'SCL_PROGRESSION' | 'QUEST_COMPLETION' | 'GITHUB_INTEGRATION' | 'TEAM_COLLABORATION'
  rarity: 'COMMON' | 'RARE' | 'EPIC' | 'LEGENDARY' | 'SACRED'
  xpBonus: number
  unlockedAt?: string
  prerequisites: string[]
}

export interface UserAchievements {
  unlockedAchievements: SacredAchievement[]
  progressTowardsAchievements: Record<string, number>
  totalAchievementXP: number
  nextAchievements: SacredAchievement[]
}

// Sacred Team Formation Types

export interface SacredTeam {
  id: string
  name: string
  description: string
  members: SacredTeamMember[]
  activeQuests: SacredQuest[]
  teamSynergy: number
  formationDate: string
  totalTeamXP: number
}

export interface SacredTeamMember {
  userId: string
  username: string
  role: BusinessTrack
  sclLevel: number
  contributionScore: number
  joinedAt: string
}

// Error Types

export interface ContributingError {
  code: string
  message: string
  details?: any
  timestamp: string
}

export interface ValidationError extends ContributingError {
  field: string
  expectedValue?: any
  actualValue?: any
}

// Event Types for Real-time Updates

export interface QuestProgressEvent {
  type: 'QUEST_STARTED' | 'QUEST_COMPLETED' | 'QUEST_CANCELLED'
  questId: string
  userId: string
  timestamp: string
  data: any
}

export interface SCLProgressEvent {
  type: 'LEVEL_UP' | 'XP_GAINED' | 'ACHIEVEMENT_UNLOCKED'
  userId: string
  timestamp: string
  data: {
    previousLevel?: number
    newLevel?: number
    xpGained?: number
    achievement?: SacredAchievement
  }
}

export interface GitHubSyncEvent {
  type: 'SYNC_STARTED' | 'SYNC_COMPLETED' | 'SYNC_FAILED'
  userId: string
  timestamp: string
  data: {
    questsProcessed?: number
    errors?: string[]
    duration?: number
  }
}
