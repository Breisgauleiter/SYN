// Sacred Consciousness Authentication Types

export interface User {
  id: string
  username: string
  email: string
  firstName?: string
  lastName?: string
  avatar?: string
  
  // Sacred Consciousness Properties
  consciousnessLevel: ConsciousnessLevel
  geneKeysProfile?: GeneKeysProfile
  sacredIntentions?: string[]
  
  // Privacy-First Properties (encrypted)
  personalVision?: string
  
  // Timestamps
  createdAt: string
  updatedAt: string
  lastActiveAt: string
}

export interface GeneKeysProfile {
  // 4 Sequences
  activationSequence: {
    sun: GeneKey
    earth: GeneKey
    northNode: GeneKey
    southNode: GeneKey
  }
  venusSequence: {
    venus: GeneKey
    pearl: GeneKey
  }
  pearlSequence: {
    pearl: GeneKey
    purpose: GeneKey
  }
  starPearlSequence: {
    starPearl: GeneKey
    divineGift: GeneKey
  }
  
  // Consciousness Evolution Data
  currentEvolutionPhase: 'SHADOW' | 'GIFT' | 'SIDDHI'
  consciousnessJourney: ConsciousnessJourneyEntry[]
}

export interface GeneKey {
  keyNumber: number // 1-64
  line: number // 1-6
  shadowAspect: string
  giftAspect: string
  siddhiAspect: string
  codonRing: number // 1-22
}

export interface ConsciousnessJourneyEntry {
  date: string
  phase: 'SHADOW' | 'GIFT' | 'SIDDHI'
  geneKey: GeneKey
  insights: string[]
  synchronicities: string[]
}

export type ConsciousnessLevel = 
  | 'AWAKENING'
  | 'EXPANDING' 
  | 'INTEGRATING'
  | 'EMBODYING'
  | 'MASTERING'

export interface AuthTokens {
  accessToken: string
  refreshToken: string
  tokenType: 'Bearer'
  expiresIn: number
  issuedAt: string
}

export interface LoginCredentials {
  email: string
  password: string
  rememberMe?: boolean
}

export interface RegisterData {
  username: string
  email: string
  password: string
  firstName?: string
  lastName?: string
  acceptTerms: boolean
  acceptPrivacyPolicy: boolean
  
  // Optional Sacred Consciousness onboarding
  initialConsciousnessLevel?: ConsciousnessLevel
  sacredIntentions?: string[]
  personalVision?: string
}

export interface AuthResponse {
  user: User
  tokens: AuthTokens
  message: string
}

export interface PasswordResetRequest {
  email: string
}

export interface PasswordReset {
  token: string
  newPassword: string
  confirmPassword: string
}

export interface ProfileUpdateData {
  firstName?: string
  lastName?: string
  avatar?: string
  personalVision?: string
  sacredIntentions?: string[]
  geneKeysProfile?: Partial<GeneKeysProfile>
}
