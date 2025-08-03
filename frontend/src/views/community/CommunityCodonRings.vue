<template>
  <div class="community-codon-rings">
    <SacredGeometryBackground />
    
    <div class="rings-container">
      <!-- Header -->
      <header class="rings-header">
        <h1 class="sacred-title">
          <span class="title-icon">🔗</span>
          Community Codon Rings
        </h1>
        <p class="rings-description">
          Explore the interconnected web of consciousness through shared Codon Rings
        </p>
        
        <div class="community-stats">
          <div class="stat-card">
            <span class="stat-number">{{ totalMembers }}</span>
            <span class="stat-label">Members</span>
          </div>
          <div class="stat-card">
            <span class="stat-number">{{ activeRings }}</span>
            <span class="stat-label">Active Rings</span>
          </div>
          <div class="stat-card">
            <span class="stat-number">{{ synchronicities }}</span>
            <span class="stat-label">Synchronicities</span>
          </div>
        </div>
      </header>

      <!-- My Codon Ring -->
      <section class="my-ring-section">
        <h2 class="section-title">
          <span class="title-icon">✨</span>
          My Codon Ring
        </h2>
        
        <div class="my-ring-card">
          <div class="ring-visualization">
            <div class="codon-ring" ref="myRingRef">
              <div class="ring-center">
                <div class="center-avatar">
                  <img :src="userAvatar" alt="Your Avatar" />
                </div>
                <div class="center-info">
                  <h3>{{ userName }}</h3>
                  <span class="consciousness-level">{{ consciousnessLevel }}</span>
                </div>
              </div>
              
              <div 
                v-for="(geneKey, index) in myGeneKeys" 
                :key="geneKey.number"
                class="gene-key-node"
                :class="{ active: geneKey.isActive }"
                :style="getNodePosition(index, myGeneKeys.length)"
                @click="selectGeneKey(geneKey)"
              >
                <div class="node-content">
                  <span class="gene-number">{{ geneKey.number }}</span>
                  <span class="gene-name">{{ geneKey.name }}</span>
                </div>
                <div class="node-connections">
                  <div 
                    v-for="connection in geneKey.connections" 
                    :key="connection.id"
                    class="connection-line"
                    :class="connection.type"
                  ></div>
                </div>
              </div>
            </div>
          </div>
          
          <div class="ring-details">
            <h3>Your Hologenetic Profile</h3>
            <div class="profile-metrics">
              <div class="metric">
                <span class="metric-label">Primary Sphere:</span>
                <span class="metric-value">{{ primarySphere }}</span>
              </div>
              <div class="metric">
                <span class="metric-label">Venus Sequence:</span>
                <span class="metric-value">{{ venusSequence }}</span>
              </div>
              <div class="metric">
                <span class="metric-label">Pearl Sequence:</span>
                <span class="metric-value">{{ pearlSequence }}</span>
              </div>
            </div>
            
            <div class="active-connections">
              <h4>Active Connections</h4>
              <div class="connections-list">
                <div 
                  v-for="connection in activeConnections" 
                  :key="connection.id"
                  class="connection-item"
                  @click="viewConnection(connection)"
                >
                  <div class="connection-avatar">
                    <img :src="connection.memberAvatar" :alt="connection.memberName" />
                  </div>
                  <div class="connection-info">
                    <span class="member-name">{{ connection.memberName }}</span>
                    <span class="connection-type">{{ connection.type }}</span>
                  </div>
                  <div class="connection-strength">
                    <div class="strength-bar">
                      <div 
                        class="strength-fill" 
                        :style="{ width: `${connection.strength}%` }"
                      ></div>
                    </div>
                    <span class="strength-value">{{ connection.strength }}%</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- Community Rings -->
      <section class="community-rings-section">
        <h2 class="section-title">
          <span class="title-icon">🌐</span>
          Community Rings
        </h2>
        
        <div class="rings-filters">
          <select v-model="selectedSphere" class="filter-select">
            <option value="">All Spheres</option>
            <option value="INSPIRATION">Inspiration</option>
            <option value="EXPRESSION">Expression</option>
            <option value="INTERACTION">Interaction</option>
          </select>
          
          <select v-model="selectedActivity" class="filter-select">
            <option value="">All Activity</option>
            <option value="HIGH">High Activity</option>
            <option value="MEDIUM">Medium Activity</option>
            <option value="LOW">Low Activity</option>
          </select>
          
          <button class="create-ring-btn" @click="createNewRing">
            <span class="btn-icon">➕</span>
            Create New Ring
          </button>
        </div>
        
        <div class="rings-grid">
          <div 
            v-for="ring in filteredCommunityRings" 
            :key="ring.id"
            class="community-ring-card"
            @click="joinRing(ring)"
          >
            <div class="ring-header">
              <h3 class="ring-name">{{ ring.name }}</h3>
              <div class="ring-status" :class="ring.status.toLowerCase()">
                {{ ring.status }}
              </div>
            </div>
            
            <div class="ring-sphere">
              <span class="sphere-icon">{{ getSphereIcon(ring.primarySphere) }}</span>
              <span class="sphere-name">{{ ring.primarySphere }}</span>
            </div>
            
            <p class="ring-description">{{ ring.description }}</p>
            
            <div class="ring-members">
              <div class="members-avatars">
                <div 
                  v-for="member in ring.members.slice(0, 5)" 
                  :key="member.id"
                  class="member-avatar"
                >
                  <img :src="member.avatar" :alt="member.name" />
                </div>
                <div 
                  v-if="ring.members.length > 5"
                  class="more-members"
                >
                  +{{ ring.members.length - 5 }}
                </div>
              </div>
              <span class="members-count">{{ ring.members.length }} members</span>
            </div>
            
            <div class="ring-metrics">
              <div class="metric">
                <span class="metric-icon">⚡</span>
                <span class="metric-value">{{ ring.activityLevel }}</span>
              </div>
              <div class="metric">
                <span class="metric-icon">🔗</span>
                <span class="metric-value">{{ ring.connectionCount }} connections</span>
              </div>
              <div class="metric">
                <span class="metric-icon">🌟</span>
                <span class="metric-value">{{ ring.synchronicityCount }} syncs</span>
              </div>
            </div>
            
            <div class="ring-actions">
              <button 
                class="join-btn"
                :class="{ joined: isJoinedRing(ring) }"
                @click.stop="isJoinedRing(ring) ? leaveRing(ring) : joinRing(ring)"
              >
                {{ isJoinedRing(ring) ? 'Leave Ring' : 'Join Ring' }}
              </button>
            </div>
          </div>
        </div>
      </section>

      <!-- Global Synchronicities -->
      <section class="synchronicities-section">
        <h2 class="section-title">
          <span class="title-icon">⚡</span>
          Global Synchronicities
        </h2>
        
        <div class="sync-timeline">
          <div 
            v-for="sync in globalSynchronicities" 
            :key="sync.id"
            class="sync-event"
            :class="sync.intensity.toLowerCase()"
          >
            <div class="sync-time">{{ formatTime(sync.timestamp) }}</div>
            <div class="sync-content">
              <div class="sync-header">
                <span class="sync-type">{{ sync.type }}</span>
                <span class="sync-intensity">{{ sync.intensity }}</span>
              </div>
              <p class="sync-description">{{ sync.description }}</p>
              <div class="sync-participants">
                <span>Involving: {{ sync.participants.join(', ') }}</span>
              </div>
            </div>
            <div class="sync-impact">
              <div class="impact-indicator" :style="{ height: `${sync.impact * 10}px` }"></div>
            </div>
          </div>
        </div>
      </section>

      <!-- Ring Creation Modal -->
      <div v-if="showCreateModal" class="modal-overlay" @click="closeCreateModal">
        <div class="create-modal" @click.stop>
          <h3>Create New Codon Ring</h3>
          
          <div class="form-group">
            <label>Ring Name</label>
            <input v-model="newRing.name" type="text" placeholder="Enter ring name" />
          </div>
          
          <div class="form-group">
            <label>Description</label>
            <textarea v-model="newRing.description" placeholder="Describe your ring's purpose"></textarea>
          </div>
          
          <div class="form-group">
            <label>Primary Sphere</label>
            <select v-model="newRing.primarySphere">
              <option value="INSPIRATION">Inspiration</option>
              <option value="EXPRESSION">Expression</option>
              <option value="INTERACTION">Interaction</option>
            </select>
          </div>
          
          <div class="form-group">
            <label>Privacy Level</label>
            <select v-model="newRing.privacy">
              <option value="PUBLIC">Public</option>
              <option value="PRIVATE">Private</option>
              <option value="INVITATION_ONLY">Invitation Only</option>
            </select>
          </div>
          
          <div class="modal-actions">
            <button class="cancel-btn" @click="closeCreateModal">Cancel</button>
            <button class="create-btn" @click="createRing">Create Ring</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useConsciousnessStore } from '@/stores/consciousness'
import SacredGeometryBackground from '@/components/sacred-geometry/SacredGeometryBackground.vue'

interface GeneKey {
  number: number
  name: string
  isActive: boolean
  connections: Connection[]
}

interface Connection {
  id: string
  type: 'RESONANCE' | 'COMPLEMENT' | 'CHALLENGE'
  memberName: string
  memberAvatar: string
  strength: number
}

interface CommunityRing {
  id: string
  name: string
  description: string
  primarySphere: 'INSPIRATION' | 'EXPRESSION' | 'INTERACTION'
  status: 'ACTIVE' | 'FORMING' | 'DORMANT'
  members: Array<{ id: string; name: string; avatar: string }>
  activityLevel: string
  connectionCount: number
  synchronicityCount: number
}

interface Synchronicity {
  id: string
  type: string
  description: string
  timestamp: string
  intensity: 'LOW' | 'MEDIUM' | 'HIGH'
  participants: string[]
  impact: number
}

const router = useRouter()
const consciousnessStore = useConsciousnessStore()

// State
const myRingRef = ref<HTMLElement>()
const selectedSphere = ref('')
const selectedActivity = ref('')
const showCreateModal = ref(false)
const newRing = ref({
  name: '',
  description: '',
  primarySphere: 'INSPIRATION',
  privacy: 'PUBLIC'
})

// User data
const userName = 'Soul Explorer'
const userAvatar = '/avatars/default-user.jpg'
const consciousnessLevel = 'EXPANDING'
const primarySphere = 'EXPRESSION'
const venusSequence = 'Venus 7'
const pearlSequence = 'Pearl 3'

// Statistics
const totalMembers = 1247
const activeRings = 23
const synchronicities = 89

// My Gene Keys
const myGeneKeys = ref<GeneKey[]>([
  { number: 1, name: 'Creativity', isActive: true, connections: [] },
  { number: 15, name: 'Extremes', isActive: false, connections: [] },
  { number: 33, name: 'Privacy', isActive: true, connections: [] },
  { number: 42, name: 'Increase', isActive: false, connections: [] },
  { number: 51, name: 'Shock', isActive: true, connections: [] },
  { number: 64, name: 'Confusion', isActive: false, connections: [] }
])

// Active connections
const activeConnections = ref<Connection[]>([
  {
    id: '1',
    type: 'RESONANCE',
    memberName: 'Luna Starweaver',
    memberAvatar: '/avatars/luna.jpg',
    strength: 85
  },
  {
    id: '2',
    type: 'COMPLEMENT',
    memberName: 'River Moonshine',
    memberAvatar: '/avatars/river.jpg',
    strength: 72
  },
  {
    id: '3',
    type: 'CHALLENGE',
    memberName: 'Phoenix Lightbringer',
    memberAvatar: '/avatars/phoenix.jpg',
    strength: 68
  }
])

// Community rings
const communityRings = ref<CommunityRing[]>([
  {
    id: '1',
    name: 'Sacred Geometry Circle',
    description: 'Exploring divine patterns through geometric consciousness',
    primarySphere: 'INSPIRATION',
    status: 'ACTIVE',
    members: [
      { id: '1', name: 'Member 1', avatar: '/avatars/m1.jpg' },
      { id: '2', name: 'Member 2', avatar: '/avatars/m2.jpg' },
      { id: '3', name: 'Member 3', avatar: '/avatars/m3.jpg' },
      { id: '4', name: 'Member 4', avatar: '/avatars/m4.jpg' },
      { id: '5', name: 'Member 5', avatar: '/avatars/m5.jpg' },
      { id: '6', name: 'Member 6', avatar: '/avatars/m6.jpg' }
    ],
    activityLevel: 'High',
    connectionCount: 42,
    synchronicityCount: 15
  },
  {
    id: '2',
    name: 'Gene Keys Integration',
    description: 'Deep dive into Gene Keys wisdom and practical application',
    primarySphere: 'EXPRESSION',
    status: 'ACTIVE',
    members: [
      { id: '7', name: 'Member 7', avatar: '/avatars/m7.jpg' },
      { id: '8', name: 'Member 8', avatar: '/avatars/m8.jpg' },
      { id: '9', name: 'Member 9', avatar: '/avatars/m9.jpg' }
    ],
    activityLevel: 'Medium',
    connectionCount: 28,
    synchronicityCount: 9
  },
  {
    id: '3',
    name: 'Consciousness Pioneers',
    description: 'Pushing the boundaries of human awareness and evolution',
    primarySphere: 'INTERACTION',
    status: 'FORMING',
    members: [
      { id: '10', name: 'Member 10', avatar: '/avatars/m10.jpg' },
      { id: '11', name: 'Member 11', avatar: '/avatars/m11.jpg' }
    ],
    activityLevel: 'Low',
    connectionCount: 12,
    synchronicityCount: 3
  }
])

// Global synchronicities
const globalSynchronicities = ref<Synchronicity[]>([
  {
    id: '1',
    type: 'CODON_ACTIVATION',
    description: 'Simultaneous activation of Codon 33 across multiple rings',
    timestamp: new Date(Date.now() - 1000 * 60 * 15).toISOString(),
    intensity: 'HIGH',
    participants: ['Luna Starweaver', 'River Moonshine', 'Phoenix Lightbringer'],
    impact: 8
  },
  {
    id: '2',
    type: 'COLLECTIVE_INSIGHT',
    description: 'Shared realization about divine timing synchronicities',
    timestamp: new Date(Date.now() - 1000 * 60 * 45).toISOString(),
    intensity: 'MEDIUM',
    participants: ['Sacred Geometry Circle members'],
    impact: 6
  },
  {
    id: '3',
    type: 'FREQUENCY_RESONANCE',
    description: 'Harmonic alignment between Venus sequences',
    timestamp: new Date(Date.now() - 1000 * 60 * 90).toISOString(),
    intensity: 'LOW',
    participants: ['Gene Keys Integration members'],
    impact: 4
  }
])

// Computed
const filteredCommunityRings = computed(() => {
  return communityRings.value.filter(ring => {
    const sphereMatch = !selectedSphere.value || ring.primarySphere === selectedSphere.value
    const activityMatch = !selectedActivity.value || 
      (selectedActivity.value === 'HIGH' && ring.activityLevel === 'High') ||
      (selectedActivity.value === 'MEDIUM' && ring.activityLevel === 'Medium') ||
      (selectedActivity.value === 'LOW' && ring.activityLevel === 'Low')
    return sphereMatch && activityMatch
  })
})

// Methods
function getNodePosition(index: number, total: number) {
  const angle = (index / total) * 2 * Math.PI - Math.PI / 2
  const radius = 120
  const x = Math.cos(angle) * radius
  const y = Math.sin(angle) * radius
  
  return {
    transform: `translate(${x}px, ${y}px)`
  }
}

function selectGeneKey(geneKey: GeneKey) {
  console.log(`🧬 Selected Gene Key ${geneKey.number}: ${geneKey.name}`)
  // Navigate to gene key detail
  router.push(`/gene-keys/${geneKey.number}`)
}

function getSphereIcon(sphere: string): string {
  const icons = {
    INSPIRATION: '💡',
    EXPRESSION: '🎨',
    INTERACTION: '🤝'
  }
  return icons[sphere as keyof typeof icons] || '⭐'
}

function isJoinedRing(ring: CommunityRing): boolean {
  // In real app, check if current user is in ring.members
  return ring.id === '1' // Example: user is in Sacred Geometry Circle
}

function joinRing(ring: CommunityRing) {
  if (!isJoinedRing(ring)) {
    console.log(`🔗 Joining ring: ${ring.name}`)
    // Add user to ring members
  }
}

function leaveRing(ring: CommunityRing) {
  console.log(`👋 Leaving ring: ${ring.name}`)
  // Remove user from ring members
}

function viewConnection(connection: Connection) {
  console.log(`👀 Viewing connection with ${connection.memberName}`)
  router.push(`/community/members/${connection.id}`)
}

function createNewRing() {
  showCreateModal.value = true
}

function closeCreateModal() {
  showCreateModal.value = false
  newRing.value = {
    name: '',
    description: '',
    primarySphere: 'INSPIRATION',
    privacy: 'PUBLIC'
  }
}

function createRing() {
  console.log('🔗 Creating new ring:', newRing.value)
  // In real app, send to API
  closeCreateModal()
}

function formatTime(timestamp: string): string {
  const now = new Date()
  const time = new Date(timestamp)
  const diff = now.getTime() - time.getTime()
  const minutes = Math.floor(diff / (1000 * 60))
  
  if (minutes < 60) {
    return `${minutes}m ago`
  } else {
    const hours = Math.floor(minutes / 60)
    return `${hours}h ago`
  }
}

onMounted(() => {
  console.log('🔗 Community Codon Rings activated')
  consciousnessStore.loadUserData()
})
</script>

<style scoped lang="scss">
.community-codon-rings {
  position: relative;
  min-height: 100vh;
  background: linear-gradient(135deg, #0a0a1a 0%, #1a0a2a 50%, #0a1a2a 100%);
}

.rings-container {
  position: relative;
  z-index: 2;
  max-width: 1400px;
  margin: 0 auto;
  padding: 2rem;
}

.rings-header {
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
  
  .rings-description {
    font-size: 1.2rem;
    color: var(--sacred-light);
    opacity: 0.9;
    margin-bottom: 2rem;
  }
  
  .community-stats {
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

.my-ring-section {
  margin-bottom: 4rem;
  
  .my-ring-card {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 3rem;
    background: rgba(255, 255, 255, 0.05);
    backdrop-filter: blur(20px);
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 20px;
    padding: 2rem;
  }
  
  .ring-visualization {
    display: flex;
    justify-content: center;
    align-items: center;
    
    .codon-ring {
      position: relative;
      width: 300px;
      height: 300px;
      
      .ring-center {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        text-align: center;
        
        .center-avatar {
          width: 80px;
          height: 80px;
          border-radius: 50%;
          overflow: hidden;
          margin: 0 auto 1rem;
          border: 3px solid var(--sacred-gold);
          
          img {
            width: 100%;
            height: 100%;
            object-fit: cover;
          }
        }
        
        .center-info {
          h3 {
            color: var(--sacred-light);
            margin-bottom: 0.25rem;
          }
          
          .consciousness-level {
            color: var(--sacred-blue);
            font-size: 0.9rem;
          }
        }
      }
      
      .gene-key-node {
        position: absolute;
        top: 50%;
        left: 50%;
        width: 60px;
        height: 60px;
        cursor: pointer;
        transition: all 0.3s ease;
        
        &.active {
          .node-content {
            background: linear-gradient(45deg, var(--sacred-gold), var(--sacred-orange));
            color: var(--sacred-dark);
          }
        }
        
        &:hover {
          transform: scale(1.1);
        }
        
        .node-content {
          width: 100%;
          height: 100%;
          background: rgba(255, 255, 255, 0.1);
          border: 2px solid var(--sacred-blue);
          border-radius: 50%;
          display: flex;
          flex-direction: column;
          align-items: center;
          justify-content: center;
          
          .gene-number {
            font-weight: 700;
            font-size: 0.9rem;
          }
          
          .gene-name {
            font-size: 0.6rem;
            text-align: center;
          }
        }
      }
    }
  }
  
  .ring-details {
    .profile-metrics {
      margin-bottom: 2rem;
      
      .metric {
        display: flex;
        justify-content: space-between;
        margin-bottom: 0.75rem;
        
        .metric-label {
          color: var(--sacred-light);
          opacity: 0.8;
        }
        
        .metric-value {
          color: var(--sacred-gold);
          font-weight: 600;
        }
      }
    }
    
    .active-connections {
      h4 {
        color: var(--sacred-blue);
        margin-bottom: 1rem;
      }
      
      .connections-list {
        .connection-item {
          display: flex;
          align-items: center;
          gap: 1rem;
          padding: 1rem;
          background: rgba(255, 255, 255, 0.05);
          border-radius: 12px;
          margin-bottom: 0.75rem;
          cursor: pointer;
          transition: all 0.3s ease;
          
          &:hover {
            background: rgba(255, 255, 255, 0.1);
          }
          
          .connection-avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            overflow: hidden;
            
            img {
              width: 100%;
              height: 100%;
              object-fit: cover;
            }
          }
          
          .connection-info {
            flex: 1;
            
            .member-name {
              display: block;
              color: var(--sacred-light);
              font-weight: 600;
            }
            
            .connection-type {
              font-size: 0.8rem;
              color: var(--sacred-blue);
            }
          }
          
          .connection-strength {
            text-align: right;
            
            .strength-bar {
              width: 60px;
              height: 6px;
              background: rgba(255, 255, 255, 0.2);
              border-radius: 3px;
              overflow: hidden;
              margin-bottom: 0.25rem;
              
              .strength-fill {
                height: 100%;
                background: linear-gradient(90deg, var(--sacred-blue), var(--sacred-purple));
              }
            }
            
            .strength-value {
              font-size: 0.8rem;
              color: var(--sacred-light);
            }
          }
        }
      }
    }
  }
}

.rings-filters {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
  align-items: center;
  
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
  
  .create-ring-btn {
    margin-left: auto;
    display: flex;
    align-items: center;
    gap: 0.5rem;
    padding: 0.75rem 1.5rem;
    background: linear-gradient(45deg, var(--sacred-gold), var(--sacred-orange));
    color: var(--sacred-dark);
    border: none;
    border-radius: 12px;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
    
    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 8px 25px rgba(255, 215, 0, 0.3);
    }
  }
}

.rings-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 2rem;
  margin-bottom: 3rem;
}

.community-ring-card {
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
  
  .ring-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 1rem;
    
    .ring-name {
      color: var(--sacred-light);
      font-size: 1.2rem;
    }
    
    .ring-status {
      padding: 0.25rem 0.75rem;
      border-radius: 12px;
      font-size: 0.8rem;
      font-weight: 600;
      
      &.active {
        background: rgba(76, 175, 80, 0.2);
        color: #4caf50;
      }
      
      &.forming {
        background: rgba(255, 193, 7, 0.2);
        color: #ffc107;
      }
      
      &.dormant {
        background: rgba(158, 158, 158, 0.2);
        color: #9e9e9e;
      }
    }
  }
  
  .ring-sphere {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    margin-bottom: 1rem;
    
    .sphere-icon {
      font-size: 1.2rem;
    }
    
    .sphere-name {
      color: var(--sacred-blue);
      font-weight: 600;
    }
  }
  
  .ring-description {
    color: var(--sacred-light);
    opacity: 0.8;
    line-height: 1.5;
    margin-bottom: 1.5rem;
  }
  
  .ring-members {
    margin-bottom: 1.5rem;
    
    .members-avatars {
      display: flex;
      gap: -0.5rem;
      margin-bottom: 0.5rem;
      
      .member-avatar {
        width: 30px;
        height: 30px;
        border-radius: 50%;
        overflow: hidden;
        border: 2px solid var(--sacred-dark);
        margin-left: -0.5rem;
        
        &:first-child {
          margin-left: 0;
        }
        
        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
        }
      }
      
      .more-members {
        width: 30px;
        height: 30px;
        border-radius: 50%;
        background: rgba(255, 255, 255, 0.1);
        border: 2px solid var(--sacred-dark);
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 0.7rem;
        color: var(--sacred-light);
        margin-left: -0.5rem;
      }
    }
    
    .members-count {
      font-size: 0.9rem;
      color: var(--sacred-light);
      opacity: 0.7;
    }
  }
  
  .ring-metrics {
    display: flex;
    gap: 1rem;
    margin-bottom: 1.5rem;
    
    .metric {
      display: flex;
      align-items: center;
      gap: 0.25rem;
      background: rgba(255, 255, 255, 0.05);
      padding: 0.5rem 0.75rem;
      border-radius: 8px;
      
      .metric-icon {
        font-size: 0.9rem;
      }
      
      .metric-value {
        font-size: 0.8rem;
        color: var(--sacred-light);
        font-weight: 600;
      }
    }
  }
  
  .ring-actions {
    .join-btn {
      width: 100%;
      padding: 0.75rem 1.5rem;
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
      
      &.joined {
        background: var(--sacred-green);
        color: var(--sacred-light);
        border-color: var(--sacred-green);
        
        &:hover {
          background: transparent;
          color: var(--sacred-green);
        }
      }
    }
  }
}

.synchronicities-section {
  .sync-timeline {
    .sync-event {
      display: flex;
      gap: 1.5rem;
      align-items: flex-start;
      padding: 1.5rem;
      background: rgba(255, 255, 255, 0.05);
      border-left: 4px solid var(--sacred-blue);
      border-radius: 0 12px 12px 0;
      margin-bottom: 1rem;
      
      &.high {
        border-left-color: var(--sacred-red);
        background: rgba(255, 0, 0, 0.05);
      }
      
      &.medium {
        border-left-color: var(--sacred-orange);
        background: rgba(255, 165, 0, 0.05);
      }
      
      &.low {
        border-left-color: var(--sacred-blue);
        background: rgba(0, 100, 255, 0.05);
      }
      
      .sync-time {
        font-size: 0.8rem;
        color: var(--sacred-light);
        opacity: 0.7;
        min-width: 60px;
      }
      
      .sync-content {
        flex: 1;
        
        .sync-header {
          display: flex;
          gap: 1rem;
          margin-bottom: 0.5rem;
          
          .sync-type {
            font-weight: 600;
            color: var(--sacred-gold);
          }
          
          .sync-intensity {
            padding: 0.25rem 0.5rem;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 6px;
            font-size: 0.7rem;
          }
        }
        
        .sync-description {
          color: var(--sacred-light);
          margin-bottom: 0.5rem;
        }
        
        .sync-participants {
          font-size: 0.8rem;
          color: var(--sacred-blue);
        }
      }
      
      .sync-impact {
        .impact-indicator {
          width: 4px;
          background: linear-gradient(to top, var(--sacred-blue), var(--sacred-purple));
          border-radius: 2px;
          min-height: 20px;
        }
      }
    }
  }
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  
  .create-modal {
    background: var(--sacred-dark);
    border: 1px solid rgba(255, 255, 255, 0.2);
    border-radius: 20px;
    padding: 2rem;
    max-width: 500px;
    width: 90%;
    
    h3 {
      color: var(--sacred-gold);
      margin-bottom: 2rem;
      text-align: center;
    }
    
    .form-group {
      margin-bottom: 1.5rem;
      
      label {
        display: block;
        color: var(--sacred-light);
        margin-bottom: 0.5rem;
        font-weight: 600;
      }
      
      input,
      textarea,
      select {
        width: 100%;
        background: rgba(255, 255, 255, 0.05);
        border: 1px solid rgba(255, 255, 255, 0.2);
        border-radius: 8px;
        padding: 0.75rem;
        color: var(--sacred-light);
        
        &:focus {
          outline: none;
          border-color: var(--sacred-blue);
        }
      }
      
      textarea {
        resize: vertical;
        min-height: 80px;
      }
    }
    
    .modal-actions {
      display: flex;
      gap: 1rem;
      justify-content: flex-end;
      
      .cancel-btn {
        padding: 0.75rem 1.5rem;
        background: transparent;
        color: var(--sacred-light);
        border: 1px solid rgba(255, 255, 255, 0.3);
        border-radius: 8px;
        cursor: pointer;
        
        &:hover {
          background: rgba(255, 255, 255, 0.1);
        }
      }
      
      .create-btn {
        padding: 0.75rem 1.5rem;
        background: linear-gradient(45deg, var(--sacred-gold), var(--sacred-orange));
        color: var(--sacred-dark);
        border: none;
        border-radius: 8px;
        font-weight: 600;
        cursor: pointer;
        
        &:hover {
          transform: translateY(-1px);
        }
      }
    }
  }
}

@keyframes gradientShift {
  0%, 100% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
}

@media (max-width: 768px) {
  .community-stats {
    flex-direction: column;
    align-items: center;
    gap: 1rem !important;
  }
  
  .my-ring-card {
    grid-template-columns: 1fr !important;
  }
  
  .rings-filters {
    flex-direction: column;
    align-items: stretch;
    
    .create-ring-btn {
      margin-left: 0;
    }
  }
  
  .rings-grid {
    grid-template-columns: 1fr;
  }
  
  .sync-event {
    flex-direction: column;
    gap: 1rem !important;
  }
}
</style>
