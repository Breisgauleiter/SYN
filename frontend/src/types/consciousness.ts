// Sacred Consciousness Platform Types

export type ConsciousnessLevel = 
  | 'AWAKENING'     // Beginning consciousness journey
  | 'EXPANDING'     // Exploring and learning
  | 'INTEGRATING'   // Applying wisdom in daily life
  | 'EMBODYING'     // Living from higher consciousness
  | 'MASTERING'     // Teaching and guiding others

export interface SacredMetrics {
  consciousnessScore: number        // 0-100
  synchronicityFrequency: number    // Events per day
  geneKeysAlignment: number         // 0-100
  sacredGeometryResonance: number   // 0-100
  fibonacciHarmony: number          // 0-100
}

export interface SynchronicityEvent {
  id: string
  timestamp: string
  type: SynchronicityType
  description: string
  significance: 'LOW' | 'MEDIUM' | 'HIGH' | 'PROFOUND'
  geneKeysInvolved?: number[]
  relatedUsers?: string[]
  contextData?: Record<string, any>
}

export type SynchronicityType =
  | 'CONSCIOUSNESS_ELEVATION'
  | 'GENE_KEYS_ACTIVATION'
  | 'SACRED_GEOMETRY_ALIGNMENT'
  | 'FIBONACCI_RESONANCE'
  | 'COMMUNITY_CONNECTION'
  | 'QUEST_COMPLETION'
  | 'INSIGHT_BREAKTHROUGH'
  | 'SERENDIPITOUS_MEETING'
  | 'PERFECT_TIMING'
  | 'SACRED_REFLECTION'

export interface ConsciousnessJourney {
  startDate: string
  currentPhase: ConsciousnessLevel
  milestones: ConsciousnessMilestone[]
  insights: ConsciousnessInsight[]
  challenges: ConsciousnessChallenge[]
  goals: ConsciousnessGoal[]
}

export interface ConsciousnessMilestone {
  id: string
  date: string
  level: ConsciousnessLevel
  title: string
  description: string
  significance: number // 1-100
  celebrationData?: {
    type: 'MEDITATION' | 'SHARING' | 'CREATION' | 'SERVICE'
    details: string
  }
}

export interface ConsciousnessInsight {
  id: string
  timestamp: string
  category: 'SELF_AWARENESS' | 'RELATIONSHIPS' | 'PURPOSE' | 'WISDOM' | 'SERVICE'
  insight: string
  geneKeysContext?: number[]
  emotionalState: 'JOYFUL' | 'PEACEFUL' | 'GRATEFUL' | 'LOVING' | 'WISE'
  integrationActions: string[]
}

export interface ConsciousnessChallenge {
  id: string
  timestamp: string
  type: 'SHADOW_WORK' | 'RESISTANCE' | 'INTEGRATION' | 'SERVICE' | 'SURRENDER'
  description: string
  currentStatus: 'ACTIVE' | 'PROCESSING' | 'INTEGRATING' | 'RESOLVED'
  supportNeeded: string[]
  learnings: string[]
}

export interface ConsciousnessGoal {
  id: string
  title: string
  description: string
  targetLevel: ConsciousnessLevel
  category: 'PERSONAL' | 'RELATIONAL' | 'PROFESSIONAL' | 'SPIRITUAL' | 'SERVICE'
  progress: number // 0-100
  milestones: string[]
  deadline?: string
  accountability?: {
    type: 'SELF' | 'BUDDY' | 'COMMUNITY' | 'MENTOR'
    details: string
  }
}

export interface SacredGeometryPattern {
  id: string
  name: string
  type: 'FIBONACCI' | 'GOLDEN_RATIO' | 'PLATONIC_SOLID' | 'MERKABA' | 'FLOWER_OF_LIFE'
  significance: string
  mathematicalProperties: {
    ratio?: number
    sequence?: number[]
    dimensions: number
    vertices?: number
    edges?: number
    faces?: number
  }
  consciousnessMapping: {
    level: ConsciousnessLevel
    geneKeysResonance: number[]
    emotionalQualities: string[]
    spiritualSignificance: string
  }
}

export interface Quest {
  id: string
  title: string
  description: string
  type: 'PLATFORM' | 'COMMUNITY' | 'LEADERSHIP' | 'HYBRID'
  difficulty: 'BEGINNER' | 'INTERMEDIATE' | 'ADVANCED' | 'MASTER'
  
  // Sacred Consciousness Requirements
  minimumLevel: ConsciousnessLevel
  geneKeysAlignment?: number[]
  
  // Quest Progress
  status: 'AVAILABLE' | 'ACTIVE' | 'COMPLETED' | 'LOCKED'
  progress: number // 0-100
  
  // Rewards
  experiencePoints: number
  consciousnessGrowth: number
  communityContribution: number
  
  // Quest Details
  objectives: QuestObjective[]
  timeline: {
    estimatedDuration: string
    deadline?: string
  }
  
  // Community Aspects
  isCollaborative: boolean
  maxParticipants?: number
  currentParticipants: string[]
  mentorAvailable: boolean
}

export interface QuestObjective {
  id: string
  title: string
  description: string
  type: 'ACTION' | 'LEARNING' | 'CREATION' | 'SHARING' | 'REFLECTION'
  isCompleted: boolean
  completedAt?: string
  evidence?: {
    type: 'TEXT' | 'IMAGE' | 'VIDEO' | 'LINK' | 'FILE'
    content: string
  }
}

// Global window interface extension for consciousness monitoring
declare global {
  interface Window {
    consciousnessMonitoringInterval?: NodeJS.Timeout | null
  }
}
