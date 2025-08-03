<template>
  <div class="syn-quests-dashboard">
    <SacredGeometryBackground />
    
    <div class="dashboard-container">
      <!-- Header -->
      <header class="dashboard-header">
        <h1 class="sacred-title">
          <span class="title-icon">🎯</span>
          SynQuests Dashboard
        </h1>
        <p class="dashboard-description">
          Embark on transformative journeys of consciousness expansion
        </p>
        
        <div class="quest-stats">
          <div class="stat-card">
            <span class="stat-number">{{ completedQuests }}</span>
            <span class="stat-label">Completed</span>
          </div>
          <div class="stat-card">
            <span class="stat-number">{{ activeQuests.length }}</span>
            <span class="stat-label">Active</span>
          </div>
          <div class="stat-card">
            <span class="stat-number">{{ totalExperience }}</span>
            <span class="stat-label">Total XP</span>
          </div>
        </div>
      </header>

      <!-- Quick Actions -->
      <section class="quick-actions">
        <button class="action-btn primary" @click="discoverNewQuests">
          <span class="btn-icon">🔍</span>
          Discover New Quests
        </button>
        
        <button class="action-btn secondary" @click="joinCommunityQuest">
          <span class="btn-icon">👥</span>
          Join Community Quest
        </button>
        
        <button class="action-btn tertiary" @click="createCustomQuest">
          <span class="btn-icon">✨</span>
          Create Custom Quest
        </button>
      </section>

      <!-- Active Quests -->
      <section class="active-quests-section">
        <h2 class="section-title">
          <span class="title-icon">⚡</span>
          Active Quests
        </h2>
        
        <div class="quests-grid">
          <div 
            v-for="quest in activeQuests" 
            :key="quest.id"
            class="quest-card active"
            @click="openQuest(quest)"
          >
            <div class="quest-header">
              <div class="quest-type">{{ getQuestTypeIcon(quest.type) }}</div>
              <div class="quest-difficulty" :class="quest.difficulty.toLowerCase()">
                {{ quest.difficulty }}
              </div>
            </div>
            
            <h3 class="quest-title">{{ quest.title }}</h3>
            <p class="quest-description">{{ quest.description }}</p>
            
            <div class="quest-progress">
              <div class="progress-bar">
                <div 
                  class="progress-fill" 
                  :style="{ width: `${quest.progress}%` }"
                ></div>
              </div>
              <span class="progress-text">{{ quest.progress }}% Complete</span>
            </div>
            
            <div class="quest-rewards">
              <div class="reward">
                <span class="reward-icon">⚡</span>
                <span class="reward-value">{{ quest.experiencePoints }} XP</span>
              </div>
              <div class="reward">
                <span class="reward-icon">🧘</span>
                <span class="reward-value">+{{ quest.consciousnessGrowth }}</span>
              </div>
            </div>
            
            <div class="quest-actions">
              <button class="continue-btn" @click.stop="continueQuest(quest)">
                Continue Quest
              </button>
            </div>
          </div>
        </div>
      </section>

      <!-- Available Quests -->
      <section class="available-quests-section">
        <h2 class="section-title">
          <span class="title-icon">🌟</span>
          Available Quests
        </h2>
        
        <div class="quest-filters">
          <select v-model="selectedType" class="filter-select">
            <option value="">All Types</option>
            <option value="PLATFORM">Platform</option>
            <option value="COMMUNITY">Community</option>
            <option value="HYBRID">Hybrid</option>
          </select>
          
          <select v-model="selectedDifficulty" class="filter-select">
            <option value="">All Difficulties</option>
            <option value="BEGINNER">Beginner</option>
            <option value="INTERMEDIATE">Intermediate</option>
            <option value="ADVANCED">Advanced</option>
          </select>
        </div>
        
        <div class="quests-grid">
          <div 
            v-for="quest in filteredAvailableQuests" 
            :key="quest.id"
            class="quest-card available"
            :class="{ locked: isQuestLocked(quest) }"
            @click="!isQuestLocked(quest) && startQuest(quest)"
          >
            <div class="quest-header">
              <div class="quest-type">{{ getQuestTypeIcon(quest.type) }}</div>
              <div class="quest-difficulty" :class="quest.difficulty.toLowerCase()">
                {{ quest.difficulty }}
              </div>
            </div>
            
            <h3 class="quest-title">{{ quest.title }}</h3>
            <p class="quest-description">{{ quest.description }}</p>
            
            <div class="quest-requirements" v-if="isQuestLocked(quest)">
              <span class="lock-icon">🔒</span>
              <span class="requirement-text">
                Requires {{ quest.minimumLevel }} consciousness level
              </span>
            </div>
            
            <div class="quest-rewards">
              <div class="reward">
                <span class="reward-icon">⚡</span>
                <span class="reward-value">{{ quest.experiencePoints }} XP</span>
              </div>
              <div class="reward">
                <span class="reward-icon">🧘</span>
                <span class="reward-value">+{{ quest.consciousnessGrowth }}</span>
              </div>
              <div class="reward" v-if="quest.communityContribution > 0">
                <span class="reward-icon">👥</span>
                <span class="reward-value">+{{ quest.communityContribution }}</span>
              </div>
            </div>
            
            <div class="quest-meta">
              <span class="quest-duration">
                <span class="meta-icon">⏱️</span>
                {{ quest.timeline.estimatedDuration }}
              </span>
              
              <span class="quest-participants" v-if="quest.isCollaborative">
                <span class="meta-icon">👥</span>
                {{ quest.currentParticipants.length }} joined
              </span>
            </div>
            
            <div class="quest-actions">
              <button 
                class="start-btn" 
                @click.stop="startQuest(quest)"
                :disabled="isQuestLocked(quest)"
              >
                {{ isQuestLocked(quest) ? 'Locked' : 'Start Quest' }}
              </button>
            </div>
          </div>
        </div>
      </section>

      <!-- Completed Quests -->
      <section class="completed-quests-section">
        <h2 class="section-title">
          <span class="title-icon">🏆</span>
          Completed Quests ({{ completedQuestsList.length }})
        </h2>
        
        <div class="quests-grid">
          <div 
            v-for="quest in completedQuestsList.slice(0, 6)" 
            :key="quest.id"
            class="quest-card completed"
            @click="reviewQuest(quest)"
          >
            <div class="quest-header">
              <div class="quest-type">{{ getQuestTypeIcon(quest.type) }}</div>
              <div class="completion-badge">✅</div>
            </div>
            
            <h3 class="quest-title">{{ quest.title }}</h3>
            <p class="quest-description">{{ quest.description }}</p>
            
            <div class="completion-stats">
              <div class="stat">
                <span class="stat-label">Completed:</span>
                <span class="stat-value">{{ formatDate(quest.completedAt) }}</span>
              </div>
              <div class="stat">
                <span class="stat-label">Rating:</span>
                <span class="stat-value">{{ '★'.repeat(quest.rating || 5) }}</span>
              </div>
            </div>
          </div>
        </div>
        
        <button 
          v-if="completedQuestsList.length > 6"
          class="view-all-btn"
          @click="viewAllCompleted"
        >
          View All Completed Quests
        </button>
      </section>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useConsciousnessStore } from '@/stores/consciousness'
import SacredGeometryBackground from '@/components/sacred-geometry/SacredGeometryBackground.vue'
import type { Quest } from '@/types/consciousness'

const router = useRouter()
const consciousnessStore = useConsciousnessStore()

// State
const selectedType = ref('')
const selectedDifficulty = ref('')

// Computed
const consciousnessLevel = computed(() => 
  consciousnessStore.journey?.currentPhase || 'AWAKENING'
)

const completedQuests = computed(() => 
  consciousnessStore.completedQuests?.length || 15
)

const totalExperience = computed(() => 
  consciousnessStore.metrics?.totalExperience || 2450
)

// Sample quest data
const activeQuests = ref<Quest[]>([
  {
    id: '1',
    title: 'Sacred Meditation Practice',
    description: 'Establish a daily 20-minute meditation practice to deepen your consciousness connection.',
    type: 'PLATFORM',
    difficulty: 'BEGINNER',
    minimumLevel: 'AWAKENING',
    status: 'ACTIVE',
    progress: 65,
    experiencePoints: 150,
    consciousnessGrowth: 25,
    communityContribution: 10,
    objectives: [],
    timeline: { estimatedDuration: '7 days' },
    isCollaborative: false,
    currentParticipants: [],
    mentorAvailable: true
  },
  {
    id: '2',
    title: 'Gene Keys Integration Workshop',
    description: 'Deep dive into your primary Gene Keys and integrate their wisdom into daily life.',
    type: 'COMMUNITY',
    difficulty: 'INTERMEDIATE',
    minimumLevel: 'EXPANDING',
    status: 'ACTIVE',
    progress: 30,
    experiencePoints: 300,
    consciousnessGrowth: 50,
    communityContribution: 30,
    objectives: [],
    timeline: { estimatedDuration: '14 days' },
    isCollaborative: true,
    currentParticipants: ['Alice', 'Bob', 'Charlie'],
    mentorAvailable: true
  }
])

const availableQuests = ref<Quest[]>([
  {
    id: '3',
    title: 'Sacred Geometry Creation',
    description: 'Create a sacred geometry artwork that represents your current consciousness level.',
    type: 'HYBRID',
    difficulty: 'ADVANCED',
    minimumLevel: 'INTEGRATING',
    status: 'AVAILABLE',
    progress: 0,
    experiencePoints: 500,
    consciousnessGrowth: 75,
    communityContribution: 50,
    objectives: [],
    timeline: { estimatedDuration: '21 days' },
    isCollaborative: false,
    currentParticipants: [],
    mentorAvailable: true
  },
  {
    id: '4',
    title: 'Community Synchronicity Circle',
    description: 'Join weekly synchronicity sharing circles to deepen collective awareness.',
    type: 'COMMUNITY',
    difficulty: 'BEGINNER',
    minimumLevel: 'AWAKENING',
    status: 'AVAILABLE',
    progress: 0,
    experiencePoints: 200,
    consciousnessGrowth: 35,
    communityContribution: 40,
    objectives: [],
    timeline: { estimatedDuration: '4 weeks' },
    isCollaborative: true,
    currentParticipants: ['David', 'Emma', 'Frank', 'Grace'],
    mentorAvailable: false
  },
  {
    id: '5',
    title: 'Digital Detox Retreat',
    description: 'Disconnect from digital devices for deeper presence and awareness.',
    type: 'PLATFORM',
    difficulty: 'INTERMEDIATE',
    minimumLevel: 'EXPANDING',
    status: 'AVAILABLE',
    progress: 0,
    experiencePoints: 350,
    consciousnessGrowth: 60,
    communityContribution: 15,
    objectives: [],
    timeline: { estimatedDuration: '3 days' },
    isCollaborative: false,
    currentParticipants: [],
    mentorAvailable: true
  }
])

const completedQuestsList = ref<Quest[]>([
  {
    id: 'c1',
    title: 'Breathwork Mastery',
    description: 'Master conscious breathing techniques for emotional regulation.',
    type: 'PLATFORM',
    difficulty: 'BEGINNER',
    minimumLevel: 'AWAKENING',
    status: 'COMPLETED',
    progress: 100,
    experiencePoints: 180,
    consciousnessGrowth: 30,
    communityContribution: 10,
    objectives: [],
    timeline: { estimatedDuration: '10 days' },
    isCollaborative: false,
    currentParticipants: [],
    mentorAvailable: true,
    completedAt: new Date(Date.now() - 1000 * 60 * 60 * 24 * 3).toISOString(),
    rating: 5
  }
])

const filteredAvailableQuests = computed(() => {
  return availableQuests.value.filter(quest => {
    const typeMatch = !selectedType.value || quest.type === selectedType.value
    const difficultyMatch = !selectedDifficulty.value || quest.difficulty === selectedDifficulty.value
    return typeMatch && difficultyMatch
  })
})

// Methods
function getQuestTypeIcon(type: string): string {
  const icons = {
    PLATFORM: '🧘',
    COMMUNITY: '👥',
    HYBRID: '🌟'
  }
  return icons[type as keyof typeof icons] || '🎯'
}

function isQuestLocked(quest: Quest): boolean {
  const levelOrder = { AWAKENING: 1, EXPANDING: 2, INTEGRATING: 3, EMBODYING: 4, MASTERING: 5 }
  const currentLevel = levelOrder[consciousnessLevel.value as keyof typeof levelOrder] || 1
  const requiredLevel = levelOrder[quest.minimumLevel as keyof typeof levelOrder] || 1
  return currentLevel < requiredLevel
}

function startQuest(quest: Quest) {
  if (!isQuestLocked(quest)) {
    console.log(`🎯 Starting quest: ${quest.title}`)
    // Move to active quests
    const index = availableQuests.value.findIndex(q => q.id === quest.id)
    if (index > -1) {
      quest.status = 'ACTIVE'
      activeQuests.value.push(quest)
      availableQuests.value.splice(index, 1)
    }
  }
}

function continueQuest(quest: Quest) {
  console.log(`⚡ Continuing quest: ${quest.title}`)
  // Navigate to quest detail view
  router.push(`/syn-quests/${quest.id}`)
}

function openQuest(quest: Quest) {
  router.push(`/syn-quests/${quest.id}`)
}

function reviewQuest(quest: Quest) {
  console.log(`🏆 Reviewing completed quest: ${quest.title}`)
  router.push(`/syn-quests/${quest.id}/review`)
}

function discoverNewQuests() {
  console.log('🔍 Discovering new quests...')
  // In real app, would fetch new quests from API
}

function joinCommunityQuest() {
  console.log('👥 Looking for community quests...')
  router.push('/community/quests')
}

function createCustomQuest() {
  console.log('✨ Creating custom quest...')
  router.push('/syn-quests/create')
}

function viewAllCompleted() {
  router.push('/syn-quests/completed')
}

function formatDate(dateString: string): string {
  const date = new Date(dateString)
  return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric' })
}

onMounted(() => {
  console.log('🎯 SynQuests Dashboard activated')
  consciousnessStore.loadUserData()
})
</script>

<style scoped lang="scss">
.syn-quests-dashboard {
  position: relative;
  min-height: 100vh;
  background: linear-gradient(135deg, #0a0a1a 0%, #1a0a2a 50%, #0a1a2a 100%);
}

.dashboard-container {
  position: relative;
  z-index: 2;
  max-width: 1400px;
  margin: 0 auto;
  padding: 2rem;
}

.dashboard-header {
  text-align: center;
  margin-bottom: 3rem;
  
  .sacred-title {
    font-size: clamp(2.5rem, 5vw, 3.5rem);
    background: linear-gradient(45deg, var(--sacred-gold), var(--sacred-blue));
    background-size: 200% 200%;
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    animation: gradientShift 3s ease-in-out infinite;
    margin-bottom: 1rem;
    
    .title-icon {
      margin-right: 0.5rem;
    }
  }
  
  .dashboard-description {
    font-size: 1.2rem;
    color: var(--sacred-light);
    opacity: 0.9;
    margin-bottom: 2rem;
  }
  
  .quest-stats {
    display: flex;
    gap: 2rem;
    justify-content: center;
    
    .stat-card {
      background: rgba(255, 255, 255, 0.05);
      backdrop-filter: blur(10px);
      border: 1px solid rgba(255, 255, 255, 0.1);
      border-radius: 16px;
      padding: 1.5rem;
      text-align: center;
      min-width: 120px;
      
      .stat-number {
        display: block;
        font-size: 2rem;
        font-weight: 700;
        color: var(--sacred-gold);
        margin-bottom: 0.25rem;
      }
      
      .stat-label {
        font-size: 0.9rem;
        color: var(--sacred-light);
        opacity: 0.8;
      }
    }
  }
}

.quick-actions {
  display: flex;
  gap: 1.5rem;
  justify-content: center;
  margin-bottom: 3rem;
  flex-wrap: wrap;
  
  .action-btn {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    padding: 1rem 2rem;
    border-radius: 12px;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
    border: 2px solid;
    
    &.primary {
      background: linear-gradient(45deg, var(--sacred-gold), var(--sacred-orange));
      color: var(--sacred-dark);
      border-color: transparent;
      
      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 8px 25px rgba(255, 215, 0, 0.3);
      }
    }
    
    &.secondary {
      background: transparent;
      color: var(--sacred-blue);
      border-color: var(--sacred-blue);
      
      &:hover {
        background: var(--sacred-blue);
        color: var(--sacred-light);
      }
    }
    
    &.tertiary {
      background: transparent;
      color: var(--sacred-purple);
      border-color: var(--sacred-purple);
      
      &:hover {
        background: var(--sacred-purple);
        color: var(--sacred-light);
      }
    }
  }
}

.section-title {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  color: var(--sacred-gold);
  font-size: 1.5rem;
  margin-bottom: 2rem;
  
  .title-icon {
    font-size: 1.3rem;
  }
}

.quest-filters {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
  
  .filter-select {
    background: rgba(255, 255, 255, 0.05);
    border: 1px solid rgba(255, 255, 255, 0.2);
    border-radius: 8px;
    padding: 0.75rem 1rem;
    color: var(--sacred-light);
    cursor: pointer;
    
    option {
      background: var(--sacred-dark);
      color: var(--sacred-light);
    }
  }
}

.quests-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 2rem;
  margin-bottom: 3rem;
}

.quest-card {
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 20px;
  padding: 2rem;
  cursor: pointer;
  transition: all 0.3s ease;
  
  &:hover {
    transform: translateY(-5px);
    border-color: var(--sacred-gold);
  }
  
  &.active {
    border-color: var(--sacred-blue);
    background: rgba(102, 102, 255, 0.05);
  }
  
  &.completed {
    border-color: var(--sacred-green);
    background: rgba(0, 255, 0, 0.03);
  }
  
  &.locked {
    opacity: 0.6;
    cursor: not-allowed;
    
    &:hover {
      transform: none;
      border-color: rgba(255, 255, 255, 0.1);
    }
  }
  
  .quest-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 1rem;
    
    .quest-type {
      font-size: 1.5rem;
    }
    
    .quest-difficulty {
      padding: 0.25rem 0.75rem;
      border-radius: 12px;
      font-size: 0.8rem;
      font-weight: 600;
      
      &.beginner {
        background: rgba(76, 175, 80, 0.2);
        color: #4caf50;
      }
      
      &.intermediate {
        background: rgba(255, 193, 7, 0.2);
        color: #ffc107;
      }
      
      &.advanced {
        background: rgba(244, 67, 54, 0.2);
        color: #f44336;
      }
    }
    
    .completion-badge {
      font-size: 1.5rem;
      color: var(--sacred-green);
    }
  }
  
  .quest-title {
    color: var(--sacred-light);
    margin-bottom: 0.75rem;
    font-size: 1.2rem;
  }
  
  .quest-description {
    color: var(--sacred-light);
    opacity: 0.8;
    line-height: 1.5;
    margin-bottom: 1.5rem;
  }
  
  .quest-progress {
    margin-bottom: 1.5rem;
    
    .progress-bar {
      height: 8px;
      background: rgba(255, 255, 255, 0.1);
      border-radius: 4px;
      overflow: hidden;
      margin-bottom: 0.5rem;
      
      .progress-fill {
        height: 100%;
        background: linear-gradient(90deg, var(--sacred-blue), var(--sacred-purple));
        transition: width 0.5s ease;
      }
    }
    
    .progress-text {
      font-size: 0.9rem;
      color: var(--sacred-blue);
    }
  }
  
  .quest-requirements {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    background: rgba(255, 0, 0, 0.1);
    border: 1px solid rgba(255, 0, 0, 0.3);
    border-radius: 8px;
    padding: 0.75rem;
    margin-bottom: 1.5rem;
    
    .lock-icon {
      font-size: 1.1rem;
    }
    
    .requirement-text {
      font-size: 0.9rem;
      color: #ff6b6b;
    }
  }
  
  .quest-rewards {
    display: flex;
    gap: 1rem;
    margin-bottom: 1.5rem;
    
    .reward {
      display: flex;
      align-items: center;
      gap: 0.25rem;
      background: rgba(255, 255, 255, 0.05);
      padding: 0.5rem 0.75rem;
      border-radius: 8px;
      
      .reward-icon {
        font-size: 0.9rem;
      }
      
      .reward-value {
        font-size: 0.8rem;
        color: var(--sacred-light);
        font-weight: 600;
      }
    }
  }
  
  .quest-meta {
    display: flex;
    gap: 1rem;
    margin-bottom: 1.5rem;
    font-size: 0.9rem;
    color: var(--sacred-light);
    opacity: 0.7;
    
    .meta-icon {
      margin-right: 0.25rem;
    }
  }
  
  .quest-actions {
    .continue-btn,
    .start-btn {
      width: 100%;
      padding: 0.75rem 1.5rem;
      background: linear-gradient(45deg, var(--sacred-gold), var(--sacred-orange));
      color: var(--sacred-dark);
      border: none;
      border-radius: 12px;
      font-weight: 600;
      cursor: pointer;
      transition: all 0.3s ease;
      
      &:hover:not(:disabled) {
        transform: translateY(-2px);
        box-shadow: 0 5px 15px rgba(255, 215, 0, 0.3);
      }
      
      &:disabled {
        opacity: 0.5;
        cursor: not-allowed;
        transform: none;
      }
    }
    
    .start-btn:disabled {
      background: rgba(255, 255, 255, 0.1);
      color: var(--sacred-light);
    }
  }
  
  .completion-stats {
    .stat {
      display: flex;
      justify-content: space-between;
      margin-bottom: 0.5rem;
      font-size: 0.9rem;
      
      .stat-label {
        color: var(--sacred-light);
        opacity: 0.7;
      }
      
      .stat-value {
        color: var(--sacred-gold);
      }
    }
  }
}

.view-all-btn {
  display: block;
  margin: 2rem auto 0;
  padding: 1rem 2rem;
  background: transparent;
  color: var(--sacred-blue);
  border: 2px solid var(--sacred-blue);
  border-radius: 12px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  
  &:hover {
    background: var(--sacred-blue);
    color: var(--sacred-light);
  }
}

@keyframes gradientShift {
  0%, 100% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
}

@media (max-width: 768px) {
  .quest-stats {
    flex-direction: column;
    align-items: center;
    gap: 1rem !important;
  }
  
  .quick-actions {
    flex-direction: column;
    align-items: center;
  }
  
  .quest-filters {
    flex-direction: column;
  }
  
  .quests-grid {
    grid-template-columns: 1fr;
  }
}
</style>
