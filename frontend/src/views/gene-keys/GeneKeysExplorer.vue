<template>
  <div class="gene-keys-explorer">
    <SacredGeometryBackground />
    
    <div class="explorer-container">
      <!-- Header -->
      <header class="explorer-header">
        <h1 class="sacred-title">
          <span class="title-icon">🔑</span>
          Gene Keys Explorer
        </h1>
        <p class="explorer-description">
          Discover the hidden codes within your DNA and unlock your highest potential
        </p>
      </header>

      <!-- Main Content -->
      <div class="explorer-content">
        <!-- Hologenetic Profile -->
        <section class="hologenetic-profile card">
          <h2 class="section-title">
            <span class="title-icon">🧬</span>
            Your Hologenetic Profile
          </h2>
          
          <div class="profile-wheel">
            <div class="wheel-center">
              <div class="consciousness-level">{{ consciousnessLevel }}</div>
            </div>
            
            <div class="gene-key-positions">
              <div 
                v-for="position in geneKeyPositions" 
                :key="position.name"
                class="gene-key-position"
                :class="position.type"
                @click="selectGeneKey(position)"
              >
                <div class="position-number">{{ position.geneKey }}</div>
                <div class="position-name">{{ position.name }}</div>
              </div>
            </div>
          </div>
          
          <div class="profile-summary">
            <div class="primary-keys">
              <div class="key-group">
                <h3>Core Sequence</h3>
                <div class="key-items">
                  <div class="key-item primary">
                    <span class="key-number">{{ profile.lifeWork }}</span>
                    <span class="key-name">Life's Work</span>
                  </div>
                  <div class="key-item">
                    <span class="key-number">{{ profile.evolution }}</span>
                    <span class="key-name">Evolution</span>
                  </div>
                  <div class="key-item">
                    <span class="key-number">{{ profile.radiance }}</span>
                    <span class="key-name">Radiance</span>
                  </div>
                  <div class="key-item">
                    <span class="key-number">{{ profile.purpose }}</span>
                    <span class="key-name">Purpose</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>

        <!-- Selected Gene Key Details -->
        <section class="gene-key-details card" v-if="selectedGeneKey">
          <h2 class="section-title">
            <span class="title-icon">🔍</span>
            Gene Key {{ selectedGeneKey.geneKey }} - {{ selectedGeneKey.name }}
          </h2>
          
          <div class="key-spectrum">
            <div class="spectrum-level shadow">
              <div class="level-header">
                <span class="level-name">Shadow</span>
                <span class="level-icon">🌑</span>
              </div>
              <h3>{{ getGeneKeyData(selectedGeneKey.geneKey).shadow }}</h3>
              <p>{{ getGeneKeyData(selectedGeneKey.geneKey).shadowDescription }}</p>
            </div>
            
            <div class="spectrum-level gift">
              <div class="level-header">
                <span class="level-name">Gift</span>
                <span class="level-icon">🎁</span>
              </div>
              <h3>{{ getGeneKeyData(selectedGeneKey.geneKey).gift }}</h3>
              <p>{{ getGeneKeyData(selectedGeneKey.geneKey).giftDescription }}</p>
            </div>
            
            <div class="spectrum-level siddhi">
              <div class="level-header">
                <span class="level-name">Siddhi</span>
                <span class="level-icon">✨</span>
              </div>
              <h3>{{ getGeneKeyData(selectedGeneKey.geneKey).siddhi }}</h3>
              <p>{{ getGeneKeyData(selectedGeneKey.geneKey).siddhiDescription }}</p>
            </div>
          </div>
          
          <div class="key-insights">
            <h3>Integration Practices</h3>
            <ul class="practice-list">
              <li v-for="practice in getGeneKeyData(selectedGeneKey.geneKey).practices" :key="practice">
                {{ practice }}
              </li>
            </ul>
          </div>
        </section>

        <!-- I Ching Hexagram -->
        <section class="iching-hexagram card" v-if="selectedGeneKey">
          <h2 class="section-title">
            <span class="title-icon">☯️</span>
            I Ching Hexagram {{ selectedGeneKey.geneKey }}
          </h2>
          
          <div class="hexagram-visual">
            <div class="hexagram-lines">
              <div 
                v-for="(line, index) in getHexagramLines(selectedGeneKey.geneKey)" 
                :key="index"
                class="hexagram-line"
                :class="{ broken: !line, solid: line }"
              ></div>
            </div>
          </div>
          
          <div class="hexagram-info">
            <h3>{{ getHexagramName(selectedGeneKey.geneKey) }}</h3>
            <p>{{ getHexagramMeaning(selectedGeneKey.geneKey) }}</p>
          </div>
        </section>

        <!-- Codon Rings -->
        <section class="codon-rings card">
          <h2 class="section-title">
            <span class="title-icon">🔗</span>
            Sacred Codon Rings
          </h2>
          
          <div class="rings-grid">
            <div 
              v-for="ring in codonRings" 
              :key="ring.name"
              class="ring-card"
              @click="exploreRing(ring)"
            >
              <div class="ring-symbol">{{ ring.symbol }}</div>
              <h3>{{ ring.name }}</h3>
              <p>{{ ring.description }}</p>
              <div class="ring-keys">
                <span 
                  v-for="key in ring.geneKeys" 
                  :key="key"
                  class="ring-key"
                  :class="{ active: isKeyInProfile(key) }"
                >
                  {{ key }}
                </span>
              </div>
            </div>
          </div>
        </section>

        <!-- Venus Sequence -->
        <section class="venus-sequence card">
          <h2 class="section-title">
            <span class="title-icon">♀</span>
            Venus Sequence - Relationship Gifts
          </h2>
          
          <div class="venus-keys">
            <div class="venus-key" v-for="(key, index) in venusSequence" :key="index">
              <div class="key-position">{{ key.position }}</div>
              <div class="key-number">{{ key.geneKey }}</div>
              <div class="key-description">{{ key.description }}</div>
            </div>
          </div>
        </section>

        <!-- Pearl Sequence -->
        <section class="pearl-sequence card">
          <h2 class="section-title">
            <span class="title-icon">🔮</span>
            Pearl Sequence - Prosperity Path
          </h2>
          
          <div class="pearl-keys">
            <div class="pearl-key" v-for="(key, index) in pearlSequence" :key="index">
              <div class="key-position">{{ key.position }}</div>
              <div class="key-number">{{ key.geneKey }}</div>
              <div class="key-description">{{ key.description }}</div>
            </div>
          </div>
        </section>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useConsciousnessStore } from '@/stores/consciousness'
import SacredGeometryBackground from '@/components/sacred-geometry/SacredGeometryBackground.vue'

const consciousnessStore = useConsciousnessStore()

// State
const selectedGeneKey = ref<any>(null)

// Computed
const consciousnessLevel = computed(() => 
  consciousnessStore.journey?.currentPhase || 'AWAKENING'
)

// Sample data - in real app this would come from birth data calculation
const profile = ref({
  lifeWork: 64,
  evolution: 47,
  radiance: 6,
  purpose: 18,
  attraction: 39,
  iq: 33,
  eq: 21,
  sq: 12
})

const geneKeyPositions = ref([
  { name: "Life's Work", geneKey: 64, type: 'primary', angle: 0 },
  { name: 'Evolution', geneKey: 47, type: 'primary', angle: 45 },
  { name: 'Radiance', geneKey: 6, type: 'primary', angle: 90 },
  { name: 'Purpose', geneKey: 18, type: 'primary', angle: 135 },
  { name: 'Attraction', geneKey: 39, type: 'secondary', angle: 180 },
  { name: 'IQ', geneKey: 33, type: 'secondary', angle: 225 },
  { name: 'EQ', geneKey: 21, type: 'secondary', angle: 270 },
  { name: 'SQ', geneKey: 12, type: 'secondary', angle: 315 }
])

const codonRings = ref([
  {
    name: 'Ring of Water',
    symbol: '💧',
    description: 'Emotional mastery and flow',
    geneKeys: [29, 59, 61, 27, 24, 42]
  },
  {
    name: 'Ring of Earth', 
    symbol: '🌍',
    description: 'Grounding and manifestation',
    geneKeys: [2, 23, 8, 20, 16, 35]
  },
  {
    name: 'Ring of Fire',
    symbol: '🔥', 
    description: 'Transformation and passion',
    geneKeys: [30, 55, 37, 63, 22, 36]
  },
  {
    name: 'Ring of Light',
    symbol: '☀️',
    description: 'Illumination and clarity',
    geneKeys: [13, 49, 30, 55, 37, 63]
  }
])

const venusSequence = ref([
  { position: 'Attraction', geneKey: 39, description: 'How you attract others' },
  { position: 'Receptivity', geneKey: 21, description: 'How you receive love' },
  { position: 'Magnetism', geneKey: 33, description: 'Your magnetic essence' },
  { position: 'Devotion', geneKey: 12, description: 'Your path of devotion' }
])

const pearlSequence = ref([
  { position: 'Foundation', geneKey: 42, description: 'Your prosperity foundation' },
  { position: 'Manifestation', geneKey: 3, description: 'How you manifest abundance' },
  { position: 'Flow', geneKey: 27, description: 'Your abundance flow' },
  { position: 'Mastery', geneKey: 50, description: 'Your prosperity mastery' }
])

// Methods
function selectGeneKey(position: any) {
  selectedGeneKey.value = position
  console.log(`🔑 Selected Gene Key ${position.geneKey} - ${position.name}`)
}

function getGeneKeyData(geneKey: number) {
  // Sample data - in real app this would be from a comprehensive database
  const geneKeyDatabase: Record<number, any> = {
    64: {
      shadow: 'Confusion',
      gift: 'Illumination', 
      siddhi: 'Truth',
      shadowDescription: 'Mental confusion and overwhelm before breakthrough',
      giftDescription: 'The ability to see clearly through complexity',
      siddhiDescription: 'Direct knowing of universal truth',
      practices: [
        'Daily meditation to quiet mental noise',
        'Study ancient wisdom traditions',
        'Practice discernment in information consumption',
        'Cultivate patience with the unknown'
      ]
    },
    47: {
      shadow: 'Oppression',
      gift: 'Transfiguration',
      siddhi: 'Transfiguration', 
      shadowDescription: 'Feeling trapped by circumstances',
      giftDescription: 'Transforming limitations into opportunities',
      siddhiDescription: 'Complete transformation of consciousness',
      practices: [
        'Reframe challenges as growth opportunities',
        'Practice emotional alchemy',
        'Study the art of transformation',
        'Embrace change as natural flow'
      ]
    },
    6: {
      shadow: 'Conflict',
      gift: 'Diplomacy',
      siddhi: 'Peace',
      shadowDescription: 'Internal and external conflicts',
      giftDescription: 'Natural peacemaking abilities',
      siddhiDescription: 'Embodiment of universal peace',
      practices: [
        'Practice active listening',
        'Study conflict resolution',
        'Mediate between opposing forces',
        'Cultivate inner harmony'
      ]
    }
  }
  
  return geneKeyDatabase[geneKey] || {
    shadow: 'Unknown',
    gift: 'Unknown',
    siddhi: 'Unknown',
    shadowDescription: 'Gene Key data not available',
    giftDescription: 'Gene Key data not available', 
    siddhiDescription: 'Gene Key data not available',
    practices: ['Explore this Gene Key through meditation']
  }
}

function getHexagramLines(geneKey: number): boolean[] {
  // Simplified hexagram generation - in real app would be accurate I Ching data
  const lines = []
  for (let i = 0; i < 6; i++) {
    lines.push((geneKey + i) % 2 === 0)
  }
  return lines
}

function getHexagramName(geneKey: number): string {
  const hexagramNames: Record<number, string> = {
    64: 'Before Completion',
    47: 'Oppression',
    6: 'Conflict',
    18: 'Work on What Has Been Spoiled'
  }
  return hexagramNames[geneKey] || `Hexagram ${geneKey}`
}

function getHexagramMeaning(geneKey: number): string {
  const meanings: Record<number, string> = {
    64: 'The potential for completion exists, but patience and careful preparation are required.',
    47: 'A time of constraint that contains the seeds of breakthrough and renewal.',
    6: 'Conflict creates the opportunity for deeper understanding and lasting peace.',
    18: 'Working to correct past patterns creates a foundation for future success.'
  }
  return meanings[geneKey] || 'Ancient wisdom encoded in this hexagram.'
}

function isKeyInProfile(geneKey: number): boolean {
  return Object.values(profile.value).includes(geneKey)
}

function exploreRing(ring: any) {
  console.log(`🔗 Exploring ${ring.name} codon ring`)
  // In real app, would navigate to detailed ring view
}

onMounted(() => {
  // Auto-select the Life's Work gene key
  selectedGeneKey.value = geneKeyPositions.value[0]
  console.log('🧬 Gene Keys Explorer activated')
})
</script>

<style scoped lang="scss">
.gene-keys-explorer {
  position: relative;
  min-height: 100vh;
  background: linear-gradient(135deg, #0a0a1a 0%, #1a0a2a 50%, #0a1a2a 100%);
}

.explorer-container {
  position: relative;
  z-index: 2;
  max-width: 1400px;
  margin: 0 auto;
  padding: 2rem;
}

.explorer-header {
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
  
  .explorer-description {
    font-size: 1.2rem;
    color: var(--sacred-light);
    opacity: 0.9;
    max-width: 600px;
    margin: 0 auto;
  }
}

.explorer-content {
  display: grid;
  grid-template-columns: 1fr;
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
    margin-bottom: 2rem;
    font-size: 1.5rem;
    
    .title-icon {
      font-size: 1.3rem;
    }
  }
}

.hologenetic-profile {
  .profile-wheel {
    position: relative;
    width: 400px;
    height: 400px;
    margin: 0 auto 2rem;
    border: 2px solid var(--sacred-gold);
    border-radius: 50%;
    
    .wheel-center {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      width: 100px;
      height: 100px;
      background: radial-gradient(circle, var(--sacred-gold), var(--sacred-blue));
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      
      .consciousness-level {
        color: var(--sacred-dark);
        font-weight: 700;
        font-size: 0.9rem;
        text-align: center;
      }
    }
    
    .gene-key-positions {
      position: relative;
      width: 100%;
      height: 100%;
    }
    
    .gene-key-position {
      position: absolute;
      width: 80px;
      height: 80px;
      border-radius: 50%;
      background: rgba(255, 255, 255, 0.1);
      border: 2px solid var(--sacred-blue);
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      transition: all 0.3s ease;
      
      &.primary {
        border-color: var(--sacred-gold);
        background: rgba(255, 215, 0, 0.1);
      }
      
      &:hover {
        transform: scale(1.1);
        box-shadow: 0 0 20px currentColor;
      }
      
      .position-number {
        font-size: 1.2rem;
        font-weight: 700;
        color: var(--sacred-light);
      }
      
      .position-name {
        font-size: 0.7rem;
        color: var(--sacred-light);
        opacity: 0.8;
        text-align: center;
      }
      
      // Position the gene keys around the wheel
      &:nth-child(1) { top: -40px; left: 50%; transform: translateX(-50%); }
      &:nth-child(2) { top: 20px; right: -40px; }
      &:nth-child(3) { top: 50%; right: -40px; transform: translateY(-50%); }
      &:nth-child(4) { bottom: 20px; right: -40px; }
      &:nth-child(5) { bottom: -40px; left: 50%; transform: translateX(-50%); }
      &:nth-child(6) { bottom: 20px; left: -40px; }
      &:nth-child(7) { top: 50%; left: -40px; transform: translateY(-50%); }
      &:nth-child(8) { top: 20px; left: -40px; }
    }
  }
  
  .profile-summary {
    .primary-keys {
      .key-group {
        h3 {
          color: var(--sacred-gold);
          margin-bottom: 1rem;
        }
        
        .key-items {
          display: grid;
          grid-template-columns: repeat(4, 1fr);
          gap: 1rem;
          
          .key-item {
            background: rgba(255, 255, 255, 0.05);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 12px;
            padding: 1rem;
            text-align: center;
            
            &.primary {
              border-color: var(--sacred-gold);
              background: rgba(255, 215, 0, 0.1);
            }
            
            .key-number {
              display: block;
              font-size: 1.5rem;
              font-weight: 700;
              color: var(--sacred-gold);
              margin-bottom: 0.5rem;
            }
            
            .key-name {
              font-size: 0.9rem;
              color: var(--sacred-light);
              opacity: 0.8;
            }
          }
        }
      }
    }
  }
}

.gene-key-details {
  .key-spectrum {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 1.5rem;
    margin-bottom: 2rem;
    
    .spectrum-level {
      background: rgba(255, 255, 255, 0.03);
      border-radius: 12px;
      padding: 1.5rem;
      
      &.shadow {
        border-left: 4px solid #ff4757;
      }
      
      &.gift {
        border-left: 4px solid var(--sacred-blue);
      }
      
      &.siddhi {
        border-left: 4px solid var(--sacred-gold);
      }
      
      .level-header {
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 1rem;
        
        .level-name {
          font-weight: 600;
          color: var(--sacred-light);
        }
        
        .level-icon {
          font-size: 1.2rem;
        }
      }
      
      h3 {
        color: var(--sacred-gold);
        margin-bottom: 0.75rem;
      }
      
      p {
        color: var(--sacred-light);
        opacity: 0.8;
        line-height: 1.5;
        font-size: 0.9rem;
      }
    }
  }
  
  .key-insights {
    h3 {
      color: var(--sacred-gold);
      margin-bottom: 1rem;
    }
    
    .practice-list {
      list-style: none;
      padding: 0;
      
      li {
        background: rgba(255, 255, 255, 0.03);
        padding: 0.75rem 1rem;
        margin-bottom: 0.5rem;
        border-radius: 8px;
        color: var(--sacred-light);
        position: relative;
        padding-left: 2rem;
        
        &::before {
          content: '✨';
          position: absolute;
          left: 0.75rem;
        }
      }
    }
  }
}

.iching-hexagram {
  .hexagram-visual {
    display: flex;
    justify-content: center;
    margin-bottom: 2rem;
    
    .hexagram-lines {
      display: flex;
      flex-direction: column-reverse;
      gap: 0.5rem;
      
      .hexagram-line {
        width: 80px;
        height: 8px;
        background: var(--sacred-gold);
        
        &.broken {
          background: linear-gradient(90deg, var(--sacred-gold) 0%, var(--sacred-gold) 35%, transparent 35%, transparent 65%, var(--sacred-gold) 65%, var(--sacred-gold) 100%);
        }
      }
    }
  }
  
  .hexagram-info {
    text-align: center;
    
    h3 {
      color: var(--sacred-gold);
      margin-bottom: 1rem;
    }
    
    p {
      color: var(--sacred-light);
      opacity: 0.9;
      line-height: 1.6;
    }
  }
}

.rings-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 1.5rem;
  
  .ring-card {
    background: rgba(255, 255, 255, 0.03);
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 16px;
    padding: 1.5rem;
    cursor: pointer;
    transition: all 0.3s ease;
    
    &:hover {
      transform: translateY(-3px);
      border-color: var(--sacred-blue);
    }
    
    .ring-symbol {
      font-size: 2rem;
      text-align: center;
      margin-bottom: 1rem;
    }
    
    h3 {
      color: var(--sacred-gold);
      margin-bottom: 0.5rem;
      text-align: center;
    }
    
    p {
      color: var(--sacred-light);
      opacity: 0.8;
      text-align: center;
      margin-bottom: 1rem;
      font-size: 0.9rem;
    }
    
    .ring-keys {
      display: flex;
      flex-wrap: wrap;
      gap: 0.5rem;
      justify-content: center;
      
      .ring-key {
        background: rgba(255, 255, 255, 0.1);
        padding: 0.25rem 0.5rem;
        border-radius: 6px;
        font-size: 0.8rem;
        color: var(--sacred-light);
        
        &.active {
          background: var(--sacred-gold);
          color: var(--sacred-dark);
        }
      }
    }
  }
}

.venus-keys,
.pearl-keys {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1rem;
  
  .venus-key,
  .pearl-key {
    background: rgba(255, 255, 255, 0.03);
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 12px;
    padding: 1.5rem;
    text-align: center;
    
    .key-position {
      color: var(--sacred-gold);
      font-weight: 600;
      margin-bottom: 0.5rem;
    }
    
    .key-number {
      font-size: 1.8rem;
      font-weight: 700;
      color: var(--sacred-blue);
      margin-bottom: 0.5rem;
    }
    
    .key-description {
      color: var(--sacred-light);
      opacity: 0.8;
      font-size: 0.9rem;
    }
  }
}

@keyframes gradientShift {
  0%, 100% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
}

@media (max-width: 768px) {
  .profile-wheel {
    width: 300px !important;
    height: 300px !important;
  }
  
  .key-spectrum {
    grid-template-columns: 1fr !important;
  }
  
  .key-items {
    grid-template-columns: repeat(2, 1fr) !important;
  }
}
</style>
