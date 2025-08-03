<template>
  <div class="consciousness-profile-page">
    <SacredGeometryBackground />
    
    <div class="profile-container">
      <!-- Header Section -->
      <header class="profile-header">
        <div class="consciousness-avatar">
          <div class="avatar-glow"></div>
          <div class="consciousness-level-indicator">
            <span class="level-text">{{ consciousnessLevel }}</span>
          </div>
        </div>
        
        <div class="profile-info">
          <h1 class="sacred-name">{{ user?.firstName }} {{ user?.lastName }}</h1>
          <p class="consciousness-title">{{ formatConsciousnessLevel(consciousnessLevel) }}</p>
          <div class="consciousness-metrics">
            <div class="metric">
              <span class="metric-value">{{ consciousnessScore }}</span>
              <span class="metric-label">Consciousness Score</span>
            </div>
            <div class="metric">
              <span class="metric-value">{{ synchronicityCount }}</span>
              <span class="metric-label">Synchronicities</span>
            </div>
            <div class="metric">
              <span class="metric-value">{{ completedQuests }}</span>
              <span class="metric-label">Quests Completed</span>
            </div>
          </div>
        </div>
      </header>

      <!-- Main Content Grid -->
      <div class="profile-content">
        <!-- Consciousness Journey -->
        <section class="consciousness-journey card">
          <h2 class="section-title">
            <span class="title-icon">🧘</span>
            Sacred Journey
          </h2>
          
          <div class="journey-progression">
            <div class="consciousness-levels">
              <div 
                v-for="level in consciousnessLevels" 
                :key="level.name"
                class="level-node"
                :class="{ 
                  active: level.name === consciousnessLevel,
                  completed: level.order < getCurrentLevelOrder(),
                  locked: level.order > getCurrentLevelOrder() + 1
                }"
              >
                <div class="level-circle">
                  <span class="level-emoji">{{ level.emoji }}</span>
                </div>
                <span class="level-name">{{ level.name }}</span>
              </div>
            </div>
            
            <div class="progress-bar">
              <div 
                class="progress-fill" 
                :style="{ width: `${consciousnessProgress}%` }"
              ></div>
            </div>
          </div>
        </section>

        <!-- Gene Keys Profile -->
        <section class="gene-keys-profile card">
          <h2 class="section-title">
            <span class="title-icon">🔑</span>
            Gene Keys Profile
          </h2>
          
          <div class="gene-keys-grid">
            <div class="gene-key-card primary">
              <h3>Life's Work</h3>
              <div class="gene-key-number">{{ primaryGeneKey.lifeWork }}</div>
              <p class="gene-key-name">{{ getGeneKeyName(primaryGeneKey.lifeWork) }}</p>
            </div>
            
            <div class="gene-key-card">
              <h3>Evolution</h3>
              <div class="gene-key-number">{{ primaryGeneKey.evolution }}</div>
              <p class="gene-key-name">{{ getGeneKeyName(primaryGeneKey.evolution) }}</p>
            </div>
            
            <div class="gene-key-card">
              <h3>Radiance</h3>
              <div class="gene-key-number">{{ primaryGeneKey.radiance }}</div>
              <p class="gene-key-name">{{ getGeneKeyName(primaryGeneKey.radiance) }}</p>
            </div>
            
            <div class="gene-key-card">
              <h3>Purpose</h3>
              <div class="gene-key-number">{{ primaryGeneKey.purpose }}</div>
              <p class="gene-key-name">{{ getGeneKeyName(primaryGeneKey.purpose) }}</p>
            </div>
          </div>
          
          <router-link to="/gene-keys/explorer" class="explore-btn">
            <span>🧬</span>
            Explore Full Profile
          </router-link>
        </section>

        <!-- Active Quests -->
        <section class="active-quests card">
          <h2 class="section-title">
            <span class="title-icon">🎯</span>
            Active Quests
          </h2>
          
          <div class="quest-list">
            <div 
              v-for="quest in activeQuests" 
              :key="quest.id"
              class="quest-item"
            >
              <div class="quest-icon">{{ getQuestIcon(quest.type) }}</div>
              <div class="quest-info">
                <h4>{{ quest.title }}</h4>
                <p>{{ quest.description }}</p>
                <div class="quest-progress">
                  <div class="progress-bar">
                    <div 
                      class="progress-fill" 
                      :style="{ width: `${quest.progress}%` }"
                    ></div>
                  </div>
                  <span class="progress-text">{{ quest.progress }}%</span>
                </div>
              </div>
            </div>
          </div>
          
          <router-link to="/syn-quests" class="explore-btn">
            <span>🗺️</span>
            Quest Dashboard
          </router-link>
        </section>

        <!-- Synchronicities -->
        <section class="synchronicities card">
          <h2 class="section-title">
            <span class="title-icon">✨</span>
            Recent Synchronicities
          </h2>
          
          <div class="synchronicity-list">
            <div 
              v-for="sync in recentSynchronicities" 
              :key="sync.id"
              class="synchronicity-item"
            >
              <div class="sync-icon">🌟</div>
              <div class="sync-content">
                <p>{{ sync.description }}</p>
                <span class="sync-time">{{ formatTime(sync.timestamp) }}</span>
              </div>
            </div>
          </div>
          
          <button class="add-synchronicity-btn" @click="showAddSynchronicity = true">
            <span>➕</span>
            Add Synchronicity
          </button>
        </section>

        <!-- Sacred Geometry Patterns -->
        <section class="sacred-patterns card">
          <h2 class="section-title">
            <span class="title-icon">🌀</span>
            Sacred Patterns
          </h2>
          
          <div class="patterns-grid">
            <div class="pattern-item" v-for="pattern in sacredPatterns" :key="pattern.id">
              <div class="pattern-visual" :style="{ background: pattern.gradient }">
                <span class="pattern-symbol">{{ pattern.symbol }}</span>
              </div>
              <span class="pattern-name">{{ pattern.name }}</span>
            </div>
          </div>
          
          <router-link to="/sacred-geometry" class="explore-btn">
            <span>🔮</span>
            Sacred Geometry Lab
          </router-link>
        </section>
      </div>
    </div>

    <!-- Add Synchronicity Modal -->
    <div v-if="showAddSynchronicity" class="modal-overlay" @click="showAddSynchronicity = false">
      <div class="modal-content" @click.stop>
        <h3>Add Sacred Synchronicity</h3>
        <textarea 
          v-model="newSynchronicity"
          placeholder="Describe your synchronicity experience..."
          rows="4"
        ></textarea>
        <div class="modal-actions">
          <button @click="showAddSynchronicity = false" class="btn-secondary">Cancel</button>
          <button @click="addSynchronicity" class="btn-primary">Add Synchronicity</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { useConsciousnessStore } from '@/stores/consciousness'
import SacredGeometryBackground from '@/components/sacred-geometry/SacredGeometryBackground.vue'

const authStore = useAuthStore()
const consciousnessStore = useConsciousnessStore()

// State
const showAddSynchronicity = ref(false)
const newSynchronicity = ref('')

// Computed
const user = computed(() => authStore.user)
const consciousnessLevel = computed(() => 
  consciousnessStore.journey?.currentPhase || 'AWAKENING'
)
const consciousnessScore = computed(() => 
  consciousnessStore.metrics?.consciousnessScore || 0
)
const synchronicityCount = computed(() => 
  consciousnessStore.synchronicities?.length || 0
)
const completedQuests = computed(() => 
  consciousnessStore.completedQuests?.length || 0
)
const consciousnessProgress = computed(() => {
  const currentLevel = consciousnessLevels.find(l => l.name === consciousnessLevel.value)
  return currentLevel ? (currentLevel.order / consciousnessLevels.length) * 100 : 0
})

// Sample data
const consciousnessLevels = [
  { name: 'AWAKENING', order: 1, emoji: '🌅' },
  { name: 'EXPANDING', order: 2, emoji: '🌱' },
  { name: 'INTEGRATING', order: 3, emoji: '🔗' },
  { name: 'EMBODYING', order: 4, emoji: '✨' },
  { name: 'MASTERING', order: 5, emoji: '👑' }
]

const primaryGeneKey = ref({
  lifeWork: 64,
  evolution: 47,
  radiance: 6,
  purpose: 18
})

const activeQuests = ref([
  {
    id: '1',
    title: 'Sacred Meditation Practice',
    description: 'Establish daily 20-minute meditation practice',
    type: 'PLATFORM',
    progress: 65
  },
  {
    id: '2', 
    title: 'Gene Keys Integration',
    description: 'Deep dive into your primary Gene Keys',
    type: 'COMMUNITY',
    progress: 30
  }
])

const recentSynchronicities = ref([
  {
    id: '1',
    description: 'Saw repeated 11:11 while contemplating my life purpose',
    timestamp: new Date(Date.now() - 1000 * 60 * 60 * 2).toISOString()
  },
  {
    id: '2',
    description: 'Met someone who shared the exact same dream I had last night',
    timestamp: new Date(Date.now() - 1000 * 60 * 60 * 24).toISOString()
  }
])

const sacredPatterns = ref([
  { id: '1', name: 'Flower of Life', symbol: '🌸', gradient: 'radial-gradient(circle, #ffd700, #ff6b47)' },
  { id: '2', name: 'Merkaba', symbol: '🔺', gradient: 'radial-gradient(circle, #6666ff, #9966ff)' },
  { id: '3', name: 'Golden Ratio', symbol: '🌀', gradient: 'radial-gradient(circle, #ff9966, #ffcc66)' },
  { id: '4', name: 'Vesica Piscis', symbol: '🐟', gradient: 'radial-gradient(circle, #66ffcc, #66ccff)' }
])

// Methods
function getCurrentLevelOrder(): number {
  const currentLevel = consciousnessLevels.find(l => l.name === consciousnessLevel.value)
  return currentLevel?.order || 1
}

function formatConsciousnessLevel(level: string): string {
  const levelMap: Record<string, string> = {
    AWAKENING: 'Sacred Awakening',
    EXPANDING: 'Consciousness Expanding', 
    INTEGRATING: 'Wisdom Integrating',
    EMBODYING: 'Truth Embodying',
    MASTERING: 'Divine Mastering'
  }
  return levelMap[level] || 'Sacred Soul'
}

function getGeneKeyName(number: number): string {
  const geneKeyNames: Record<number, string> = {
    64: 'Confusion → Illumination',
    47: 'Oppression → Transfiguration', 
    6: 'Conflict → Peace',
    18: 'Correction → Perfection'
  }
  return geneKeyNames[number] || 'Unknown Key'
}

function getQuestIcon(type: string): string {
  const icons: Record<string, string> = {
    PLATFORM: '🧘',
    COMMUNITY: '👥',
    HYBRID: '🌟'
  }
  return icons[type] || '🎯'
}

function formatTime(timestamp: string): string {
  const now = new Date()
  const time = new Date(timestamp)
  const diffHours = Math.floor((now.getTime() - time.getTime()) / (1000 * 60 * 60))
  
  if (diffHours < 24) {
    return `${diffHours}h ago`
  } else {
    const diffDays = Math.floor(diffHours / 24)
    return `${diffDays}d ago`
  }
}

function addSynchronicity() {
  if (newSynchronicity.value.trim()) {
    recentSynchronicities.value.unshift({
      id: Date.now().toString(),
      description: newSynchronicity.value.trim(),
      timestamp: new Date().toISOString()
    })
    newSynchronicity.value = ''
    showAddSynchronicity.value = false
    console.log('✨ New synchronicity added to sacred journal')
  }
}

onMounted(() => {
  console.log('🧬 Consciousness Profile activated')
  // Load consciousness data
  consciousnessStore.loadUserData()
})
</script>

<style scoped lang="scss">
.consciousness-profile-page {
  position: relative;
  min-height: 100vh;
  background: linear-gradient(135deg, #0a0a1a 0%, #1a0a2a 50%, #0a1a2a 100%);
}

.profile-container {
  position: relative;
  z-index: 2;
  max-width: 1400px;
  margin: 0 auto;
  padding: 2rem;
}

.profile-header {
  display: flex;
  align-items: center;
  gap: 2rem;
  margin-bottom: 3rem;
  padding: 2rem;
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 24px;
}

.consciousness-avatar {
  position: relative;
  width: 120px;
  height: 120px;
  
  .avatar-glow {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    background: radial-gradient(circle, var(--sacred-gold), var(--sacred-blue));
    animation: consciousnessGlow 3s ease-in-out infinite;
  }
  
  .consciousness-level-indicator {
    position: absolute;
    bottom: -10px;
    left: 50%;
    transform: translateX(-50%);
    background: var(--sacred-gold);
    color: var(--sacred-dark);
    padding: 0.5rem 1rem;
    border-radius: 20px;
    font-weight: 600;
    font-size: 0.8rem;
  }
}

.profile-info {
  flex: 1;
  
  .sacred-name {
    font-size: 2.5rem;
    color: var(--sacred-light);
    margin-bottom: 0.5rem;
  }
  
  .consciousness-title {
    font-size: 1.2rem;
    color: var(--sacred-gold);
    margin-bottom: 1.5rem;
  }
  
  .consciousness-metrics {
    display: flex;
    gap: 2rem;
    
    .metric {
      text-align: center;
      
      .metric-value {
        display: block;
        font-size: 1.8rem;
        font-weight: 700;
        color: var(--sacred-blue);
      }
      
      .metric-label {
        font-size: 0.9rem;
        color: var(--sacred-light);
        opacity: 0.8;
      }
    }
  }
}

.profile-content {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: 2rem;
}

.card {
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 20px;
  padding: 2rem;
  transition: transform 0.3s ease;
  
  &:hover {
    transform: translateY(-5px);
    border-color: var(--sacred-gold);
  }
  
  .section-title {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    color: var(--sacred-gold);
    margin-bottom: 1.5rem;
    font-size: 1.4rem;
    
    .title-icon {
      font-size: 1.2rem;
    }
  }
}

.consciousness-journey {
  grid-column: span 2;
  
  .journey-progression {
    .consciousness-levels {
      display: flex;
      justify-content: space-between;
      margin-bottom: 1rem;
      
      .level-node {
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 0.5rem;
        opacity: 0.5;
        transition: all 0.3s ease;
        
        &.completed,
        &.active {
          opacity: 1;
        }
        
        &.active .level-circle {
          transform: scale(1.2);
          box-shadow: 0 0 20px var(--sacred-gold);
        }
        
        .level-circle {
          width: 60px;
          height: 60px;
          border-radius: 50%;
          background: rgba(255, 255, 255, 0.1);
          border: 2px solid var(--sacred-gold);
          display: flex;
          align-items: center;
          justify-content: center;
          transition: all 0.3s ease;
          
          .level-emoji {
            font-size: 1.5rem;
          }
        }
        
        .level-name {
          font-size: 0.8rem;
          color: var(--sacred-light);
        }
      }
    }
    
    .progress-bar {
      height: 8px;
      background: rgba(255, 255, 255, 0.1);
      border-radius: 4px;
      overflow: hidden;
      
      .progress-fill {
        height: 100%;
        background: linear-gradient(90deg, var(--sacred-gold), var(--sacred-blue));
        transition: width 0.5s ease;
      }
    }
  }
}

.gene-keys-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 1rem;
  margin-bottom: 1.5rem;
  
  .gene-key-card {
    background: rgba(255, 255, 255, 0.05);
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 12px;
    padding: 1.5rem;
    text-align: center;
    
    &.primary {
      grid-column: span 2;
      background: rgba(255, 215, 0, 0.1);
      border-color: var(--sacred-gold);
    }
    
    h3 {
      color: var(--sacred-light);
      margin-bottom: 0.5rem;
      font-size: 0.9rem;
    }
    
    .gene-key-number {
      font-size: 2rem;
      font-weight: 700;
      color: var(--sacred-gold);
      margin-bottom: 0.5rem;
    }
    
    .gene-key-name {
      color: var(--sacred-light);
      opacity: 0.8;
      font-size: 0.8rem;
    }
  }
}

.quest-list {
  margin-bottom: 1.5rem;
  
  .quest-item {
    display: flex;
    gap: 1rem;
    padding: 1rem;
    background: rgba(255, 255, 255, 0.03);
    border-radius: 12px;
    margin-bottom: 1rem;
    
    .quest-icon {
      font-size: 1.5rem;
      width: 40px;
      height: 40px;
      display: flex;
      align-items: center;
      justify-content: center;
      background: rgba(255, 255, 255, 0.1);
      border-radius: 8px;
    }
    
    .quest-info {
      flex: 1;
      
      h4 {
        color: var(--sacred-light);
        margin-bottom: 0.5rem;
      }
      
      p {
        color: var(--sacred-light);
        opacity: 0.7;
        margin-bottom: 0.75rem;
        font-size: 0.9rem;
      }
      
      .quest-progress {
        display: flex;
        align-items: center;
        gap: 0.75rem;
        
        .progress-bar {
          flex: 1;
          height: 6px;
          background: rgba(255, 255, 255, 0.1);
          border-radius: 3px;
          overflow: hidden;
          
          .progress-fill {
            height: 100%;
            background: var(--sacred-blue);
          }
        }
        
        .progress-text {
          font-size: 0.8rem;
          color: var(--sacred-blue);
        }
      }
    }
  }
}

.synchronicity-list {
  margin-bottom: 1.5rem;
  
  .synchronicity-item {
    display: flex;
    gap: 0.75rem;
    padding: 1rem;
    background: rgba(255, 255, 255, 0.03);
    border-radius: 12px;
    margin-bottom: 0.75rem;
    
    .sync-icon {
      font-size: 1.2rem;
    }
    
    .sync-content {
      flex: 1;
      
      p {
        color: var(--sacred-light);
        margin-bottom: 0.25rem;
        line-height: 1.4;
      }
      
      .sync-time {
        font-size: 0.8rem;
        color: var(--sacred-light);
        opacity: 0.6;
      }
    }
  }
}

.patterns-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 1rem;
  margin-bottom: 1.5rem;
  
  .pattern-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 0.5rem;
    
    .pattern-visual {
      width: 60px;
      height: 60px;
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      
      .pattern-symbol {
        font-size: 1.5rem;
        filter: drop-shadow(0 0 10px rgba(255, 255, 255, 0.5));
      }
    }
    
    .pattern-name {
      font-size: 0.8rem;
      color: var(--sacred-light);
      text-align: center;
    }
  }
}

.explore-btn {
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.75rem 1.5rem;
  background: linear-gradient(45deg, var(--sacred-blue), var(--sacred-purple));
  color: var(--sacred-light);
  text-decoration: none;
  border-radius: 12px;
  font-weight: 600;
  transition: all 0.3s ease;
  
  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 5px 15px rgba(102, 102, 255, 0.3);
  }
}

.add-synchronicity-btn {
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.75rem 1.5rem;
  background: transparent;
  color: var(--sacred-gold);
  border: 2px solid var(--sacred-gold);
  border-radius: 12px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  
  &:hover {
    background: var(--sacred-gold);
    color: var(--sacred-dark);
  }
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.8);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  
  .modal-content {
    background: rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(20px);
    border: 1px solid rgba(255, 255, 255, 0.2);
    border-radius: 20px;
    padding: 2rem;
    width: 90%;
    max-width: 500px;
    
    h3 {
      color: var(--sacred-gold);
      margin-bottom: 1rem;
    }
    
    textarea {
      width: 100%;
      padding: 1rem;
      background: rgba(255, 255, 255, 0.05);
      border: 1px solid rgba(255, 255, 255, 0.2);
      border-radius: 12px;
      color: var(--sacred-light);
      resize: vertical;
      margin-bottom: 1.5rem;
      
      &::placeholder {
        color: rgba(255, 255, 255, 0.5);
      }
    }
    
    .modal-actions {
      display: flex;
      gap: 1rem;
      justify-content: flex-end;
      
      .btn-secondary {
        padding: 0.75rem 1.5rem;
        background: transparent;
        color: var(--sacred-light);
        border: 1px solid rgba(255, 255, 255, 0.3);
        border-radius: 8px;
        cursor: pointer;
      }
      
      .btn-primary {
        padding: 0.75rem 1.5rem;
        background: var(--sacred-gold);
        color: var(--sacred-dark);
        border: none;
        border-radius: 8px;
        font-weight: 600;
        cursor: pointer;
      }
    }
  }
}

@keyframes consciousnessGlow {
  0%, 100% { box-shadow: 0 0 20px rgba(255, 215, 0, 0.5); }
  50% { box-shadow: 0 0 40px rgba(102, 102, 255, 0.7); }
}

@media (max-width: 1024px) {
  .profile-content {
    grid-template-columns: 1fr;
  }
  
  .consciousness-journey {
    grid-column: span 1;
  }
}

@media (max-width: 768px) {
  .profile-header {
    flex-direction: column;
    text-align: center;
  }
  
  .consciousness-metrics {
    justify-content: center;
  }
  
  .gene-keys-grid {
    grid-template-columns: 1fr;
    
    .gene-key-card.primary {
      grid-column: span 1;
    }
  }
  
  .patterns-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>
