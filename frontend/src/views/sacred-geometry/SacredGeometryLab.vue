<template>
  <div class="sacred-geometry-lab">
    <SacredGeometryBackground />
    
    <div class="lab-container">
      <!-- Header -->
      <header class="lab-header">
        <h1 class="sacred-title">
          <span class="title-icon">⚡</span>
          Sacred Geometry Laboratory
        </h1>
        <p class="lab-description">
          Explore, create, and embody divine geometric patterns of consciousness
        </p>
        
        <div class="lab-stats">
          <div class="stat-card">
            <span class="stat-number">{{ createdPatterns }}</span>
            <span class="stat-label">Created</span>
          </div>
          <div class="stat-card">
            <span class="stat-number">{{ favoritePatterns }}</span>
            <span class="stat-label">Favorites</span>
          </div>
          <div class="stat-card">
            <span class="stat-number">{{ sharedPatterns }}</span>
            <span class="stat-label">Shared</span>
          </div>
        </div>
      </header>

      <!-- Sacred Geometry Live Visualization -->
      <section class="sacred-live-viz">
        <SacredGeometryVisualization />
      </section>

      <!-- Sacred Canvas -->
      <section class="sacred-canvas-section">
        <h2 class="section-title">
          <span class="title-icon">🎨</span>
          Sacred Canvas
        </h2>
        
        <div class="canvas-workspace">
          <div class="canvas-container">
            <canvas 
              ref="sacredCanvas" 
              class="sacred-canvas"
              @mousedown="startDrawing"
              @mousemove="draw"
              @mouseup="stopDrawing"
              @mouseleave="stopDrawing"
            ></canvas>
            
            <div class="canvas-overlay">
              <div class="canvas-info">
                <span class="pattern-name">{{ currentPattern.name }}</span>
                <span class="pattern-frequency">{{ currentPattern.frequency }} Hz</span>
              </div>
            </div>
          </div>
          
          <div class="canvas-controls">
            <div class="tool-palette">
              <h3>Sacred Tools</h3>
              
              <div class="tool-group">
                <label>Pattern Type</label>
                <select v-model="selectedPattern" @change="changePattern">
                  <option value="flower-of-life">Flower of Life</option>
                  <option value="metatron-cube">Metatron's Cube</option>
                  <option value="golden-spiral">Golden Spiral</option>
                  <option value="merkaba">Merkaba</option>
                  <option value="sri-yantra">Sri Yantra</option>
                  <option value="mandala">Sacred Mandala</option>
                  <option value="torus">Torus Field</option>
                  <option value="platonic-solids">Platonic Solids</option>
                </select>
              </div>
              
              <div class="tool-group">
                <label>Sacred Colors</label>
                <div class="color-palette">
                  <div 
                    v-for="color in sacredColors" 
                    :key="color.name"
                    class="color-swatch"
                    :class="{ active: selectedColor === color.value }"
                    :style="{ backgroundColor: color.value }"
                    @click="selectColor(color.value)"
                    :title="color.name"
                  ></div>
                </div>
              </div>
              
              <div class="tool-group">
                <label>Frequency: {{ frequency }} Hz</label>
                <input 
                  type="range" 
                  v-model="frequency" 
                  min="40" 
                  max="40000" 
                  step="1"
                  class="frequency-slider"
                  @input="updateFrequency"
                />
              </div>
              
              <div class="tool-group">
                <label>Animation Speed</label>
                <input 
                  type="range" 
                  v-model="animationSpeed" 
                  min="0" 
                  max="10" 
                  step="0.1"
                  class="speed-slider"
                  @input="updateAnimation"
                />
              </div>
              
              <div class="tool-group">
                <label>Complexity</label>
                <input 
                  type="range" 
                  v-model="complexity" 
                  min="1" 
                  max="12" 
                  step="1"
                  class="complexity-slider"
                  @input="updateComplexity"
                />
              </div>
            </div>
            
            <div class="action-buttons">
              <button class="action-btn primary" @click="generatePattern">
                <span class="btn-icon">✨</span>
                Generate
              </button>
              
              <button class="action-btn secondary" @click="animatePattern">
                <span class="btn-icon">🌀</span>
                {{ isAnimating ? 'Pause' : 'Animate' }}
              </button>
              
              <button class="action-btn tertiary" @click="clearCanvas">
                <span class="btn-icon">🧹</span>
                Clear
              </button>
              
              <button class="action-btn quaternary" @click="savePattern">
                <span class="btn-icon">💾</span>
                Save
              </button>
              
              <button class="action-btn quaternary" @click="sharePattern">
                <span class="btn-icon">🔗</span>
                Share
              </button>
            </div>
          </div>
        </div>
      </section>

      <!-- Pattern Library -->
      <section class="pattern-library-section">
        <h2 class="section-title">
          <span class="title-icon">📚</span>
          Sacred Pattern Library
        </h2>
        
        <div class="library-filters">
          <select v-model="selectedCategory" class="filter-select">
            <option value="">All Categories</option>
            <option value="CLASSICAL">Classical Sacred</option>
            <option value="MODERN">Modern Interpretations</option>
            <option value="PERSONAL">Personal Creations</option>
            <option value="COMMUNITY">Community Shared</option>
          </select>
          
          <select v-model="selectedComplexity" class="filter-select">
            <option value="">All Complexity</option>
            <option value="SIMPLE">Simple (1-3)</option>
            <option value="MEDIUM">Medium (4-8)</option>
            <option value="COMPLEX">Complex (9-12)</option>
          </select>
          
          <button class="create-pattern-btn" @click="createNewPattern">
            <span class="btn-icon">➕</span>
            Create New
          </button>
        </div>
        
        <div class="patterns-grid">
          <div 
            v-for="pattern in filteredPatterns" 
            :key="pattern.id"
            class="pattern-card"
            @click="loadPattern(pattern)"
          >
            <div class="pattern-preview">
              <div class="pattern-image" :style="{ backgroundImage: `url(${pattern.thumbnail})` }">
                <div class="pattern-overlay">
                  <span class="pattern-frequency">{{ pattern.frequency }} Hz</span>
                </div>
              </div>
            </div>
            
            <div class="pattern-info">
              <h3 class="pattern-name">{{ pattern.name }}</h3>
              <p class="pattern-description">{{ pattern.description }}</p>
              
              <div class="pattern-meta">
                <span class="pattern-category">{{ pattern.category }}</span>
                <span class="pattern-complexity">Level {{ pattern.complexity }}</span>
              </div>
              
              <div class="pattern-stats">
                <div class="stat">
                  <span class="stat-icon">❤️</span>
                  <span class="stat-value">{{ pattern.likes }}</span>
                </div>
                <div class="stat">
                  <span class="stat-icon">👁️</span>
                  <span class="stat-value">{{ pattern.views }}</span>
                </div>
                <div class="stat">
                  <span class="stat-icon">🔗</span>
                  <span class="stat-value">{{ pattern.shares }}</span>
                </div>
              </div>
              
              <div class="pattern-actions">
                <button class="load-btn" @click.stop="loadPattern(pattern)">
                  Load Pattern
                </button>
                <button 
                  class="favorite-btn" 
                  :class="{ active: isFavorite(pattern) }"
                  @click.stop="toggleFavorite(pattern)"
                >
                  ⭐
                </button>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- Consciousness Integration -->
      <section class="consciousness-integration-section">
        <h2 class="section-title">
          <span class="title-icon">🧘</span>
          Consciousness Integration
        </h2>
        
        <div class="integration-workspace">
          <div class="meditation-space">
            <h3>Sacred Meditation</h3>
            <p>Focus on your created pattern while in meditation to integrate its frequency</p>
            
            <div class="meditation-controls">
              <button 
                class="meditation-btn"
                :class="{ active: isMeditating }"
                @click="toggleMeditation"
              >
                {{ isMeditating ? 'End Session' : 'Begin Meditation' }}
              </button>
              
              <div v-if="isMeditating" class="meditation-timer">
                <span class="timer-display">{{ formatTime(meditationTime) }}</span>
                <div class="breathing-guide">
                  <div class="breath-circle" :class="{ inhale: breathPhase === 'inhale' }"></div>
                  <span class="breath-instruction">{{ breathInstruction }}</span>
                </div>
              </div>
            </div>
          </div>
          
          <div class="frequency-analysis">
            <h3>Frequency Analysis</h3>
            <div class="frequency-display">
              <canvas ref="frequencyCanvas" class="frequency-canvas"></canvas>
            </div>
            
            <div class="harmony-metrics">
              <div class="metric">
                <span class="metric-label">Base Frequency:</span>
                <span class="metric-value">{{ currentPattern.frequency }} Hz</span>
              </div>
              <div class="metric">
                <span class="metric-label">Harmonic Ratio:</span>
                <span class="metric-value">{{ harmonicRatio }}</span>
              </div>
              <div class="metric">
                <span class="metric-label">Coherence Level:</span>
                <span class="metric-value">{{ coherenceLevel }}%</span>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- Community Showcase -->
      <section class="community-showcase-section">
        <h2 class="section-title">
          <span class="title-icon">🌟</span>
          Community Showcase
        </h2>
        
        <div class="showcase-grid">
          <div 
            v-for="showcase in communityShowcase" 
            :key="showcase.id"
            class="showcase-card"
          >
            <div class="showcase-header">
              <div class="creator-info">
                <img :src="showcase.creator.avatar" :alt="showcase.creator.name" class="creator-avatar" />
                <div class="creator-details">
                  <span class="creator-name">{{ showcase.creator.name }}</span>
                  <span class="creator-level">{{ showcase.creator.level }}</span>
                </div>
              </div>
              <div class="showcase-date">{{ formatDate(showcase.createdAt) }}</div>
            </div>
            
            <div class="showcase-content">
              <div class="pattern-showcase">
                <img :src="showcase.pattern.image" :alt="showcase.pattern.name" />
              </div>
              
              <h3 class="showcase-title">{{ showcase.title }}</h3>
              <p class="showcase-description">{{ showcase.description }}</p>
              
              <div class="showcase-insights">
                <h4>Sacred Insights</h4>
                <p>"{{ showcase.insights }}"</p>
              </div>
            </div>
            
            <div class="showcase-interaction">
              <div class="showcase-stats">
                <div class="stat">
                  <span class="stat-icon">💫</span>
                  <span class="stat-value">{{ showcase.resonanceCount }}</span>
                </div>
                <div class="stat">
                  <span class="stat-icon">💬</span>
                  <span class="stat-value">{{ showcase.commentCount }}</span>
                </div>
                <div class="stat">
                  <span class="stat-icon">🔄</span>
                  <span class="stat-value">{{ showcase.shareCount }}</span>
                </div>
              </div>
              
              <div class="showcase-actions">
                <button class="resonate-btn" @click="resonateWithShowcase(showcase)">
                  Resonate
                </button>
                <button class="comment-btn" @click="commentOnShowcase(showcase)">
                  Comment
                </button>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { useConsciousnessStore } from '@/stores/consciousness'
import SacredGeometryBackground from '@/components/sacred-geometry/SacredGeometryBackground.vue'
import SacredGeometryVisualization from '@/components/SacredGeometryVisualization.vue'

interface SacredPattern {
  id: string
  name: string
  description: string
  category: 'CLASSICAL' | 'MODERN' | 'PERSONAL' | 'COMMUNITY'
  complexity: number
  frequency: number
  thumbnail: string
  likes: number
  views: number
  shares: number
  creator?: string
  createdAt?: string
}

interface PatternShowcase {
  id: string
  title: string
  description: string
  insights: string
  pattern: { name: string; image: string }
  creator: { name: string; avatar: string; level: string }
  createdAt: string
  resonanceCount: number
  commentCount: number
  shareCount: number
}

const router = useRouter()
const consciousnessStore = useConsciousnessStore()

// Canvas refs
const sacredCanvas = ref<HTMLCanvasElement>()
const frequencyCanvas = ref<HTMLCanvasElement>()

// State
const selectedPattern = ref('flower-of-life')
const selectedColor = ref('#ffd700')
const frequency = ref(528)
const animationSpeed = ref(1)
const complexity = ref(6)
const isDrawing = ref(false)
const isAnimating = ref(false)
const animationId = ref<number>()

// Filters
const selectedCategory = ref('')
const selectedComplexity = ref('')

// Meditation
const isMeditating = ref(false)
const meditationTime = ref(0)
const meditationInterval = ref<NodeJS.Timeout>()
const breathPhase = ref<'inhale' | 'exhale'>('inhale')
const breathInstruction = ref('Breathe in...')

// Statistics
const createdPatterns = 23
const favoritePatterns = 47
const sharedPatterns = 15

// Sacred colors
const sacredColors = [
  { name: 'Sacred Gold', value: '#ffd700' },
  { name: 'Divine Blue', value: '#4169e1' },
  { name: 'Cosmic Purple', value: '#8a2be2' },
  { name: 'Heart Green', value: '#32cd32' },
  { name: 'Sunset Orange', value: '#ff6347' },
  { name: 'Crown Violet', value: '#9370db' },
  { name: 'Solar Yellow', value: '#ffff00' },
  { name: 'Ocean Teal', value: '#008080' }
]

// Current pattern
const currentPattern = ref({
  name: 'Flower of Life',
  frequency: 528
})

// Pattern library
const patternLibrary = ref<SacredPattern[]>([
  {
    id: '1',
    name: 'Flower of Life',
    description: 'Ancient symbol of creation and the fundamental forms of space and time',
    category: 'CLASSICAL',
    complexity: 4,
    frequency: 528,
    thumbnail: '/patterns/flower-of-life.jpg',
    likes: 142,
    views: 1547,
    shares: 23
  },
  {
    id: '2',
    name: 'Metatron\'s Cube',
    description: 'Sacred geometric figure containing all five Platonic solids',
    category: 'CLASSICAL',
    complexity: 8,
    frequency: 741,
    thumbnail: '/patterns/metatrons-cube.jpg',
    likes: 98,
    views: 892,
    shares: 15
  },
  {
    id: '3',
    name: 'Sri Yantra',
    description: 'Sacred geometry representing the cosmic order and divine feminine',
    category: 'CLASSICAL',
    complexity: 9,
    frequency: 852,
    thumbnail: '/patterns/sri-yantra.jpg',
    likes: 203,
    views: 2341,
    shares: 45
  },
  {
    id: '4',
    name: 'Golden Spiral Mandala',
    description: 'Personal creation combining golden ratio with mandala principles',
    category: 'PERSONAL',
    complexity: 6,
    frequency: 432,
    thumbnail: '/patterns/golden-spiral.jpg',
    likes: 67,
    views: 543,
    shares: 8
  }
])

// Community showcase
const communityShowcase = ref<PatternShowcase[]>([
  {
    id: '1',
    title: 'Awakening Through Sacred Geometry',
    description: 'My journey with the Flower of Life pattern during morning meditation',
    insights: 'The interconnectedness of all creation became viscerally real as I traced each circle with my awareness.',
    pattern: { name: 'Flower of Life', image: '/patterns/showcase1.jpg' },
    creator: { name: 'Luna Starweaver', avatar: '/avatars/luna.jpg', level: 'EXPANDING' },
    createdAt: new Date(Date.now() - 1000 * 60 * 60 * 2).toISOString(),
    resonanceCount: 24,
    commentCount: 8,
    shareCount: 5
  },
  {
    id: '2',
    title: 'Healing with Metatron\'s Cube',
    description: 'Using sacred geometry for emotional healing and chakra balancing',
    insights: 'Each vertex of the cube seemed to unlock a different aspect of my healing journey.',
    pattern: { name: 'Metatron\'s Cube', image: '/patterns/showcase2.jpg' },
    creator: { name: 'River Moonshine', avatar: '/avatars/river.jpg', level: 'INTEGRATING' },
    createdAt: new Date(Date.now() - 1000 * 60 * 60 * 6).toISOString(),
    resonanceCount: 31,
    commentCount: 12,
    shareCount: 7
  }
])

// Computed
const filteredPatterns = computed(() => {
  return patternLibrary.value.filter(pattern => {
    const categoryMatch = !selectedCategory.value || pattern.category === selectedCategory.value
    const complexityMatch = !selectedComplexity.value || 
      (selectedComplexity.value === 'SIMPLE' && pattern.complexity <= 3) ||
      (selectedComplexity.value === 'MEDIUM' && pattern.complexity >= 4 && pattern.complexity <= 8) ||
      (selectedComplexity.value === 'COMPLEX' && pattern.complexity >= 9)
    return categoryMatch && complexityMatch
  })
})

const harmonicRatio = computed(() => {
  return (frequency.value / 528).toFixed(2)
})

const coherenceLevel = computed(() => {
  // Calculate coherence based on complexity and frequency harmony
  const baseCoherence = Math.max(0, 100 - (complexity.value * 5))
  const frequencyBonus = frequency.value === 528 ? 20 : 0
  return Math.min(100, baseCoherence + frequencyBonus)
})

// Methods
function initializeCanvas() {
  if (!sacredCanvas.value) return
  
  const canvas = sacredCanvas.value
  const ctx = canvas.getContext('2d')
  if (!ctx) return
  
  // Set canvas size
  canvas.width = 600
  canvas.height = 600
  
  // Initial pattern
  generatePattern()
}

function changePattern() {
  currentPattern.value.name = selectedPattern.value.split('-').map(word => 
    word.charAt(0).toUpperCase() + word.slice(1)
  ).join(' ')
  generatePattern()
}

function selectColor(color: string) {
  selectedColor.value = color
}

function updateFrequency() {
  currentPattern.value.frequency = parseInt(frequency.value.toString())
  generatePattern()
}

function updateAnimation() {
  if (isAnimating.value) {
    startAnimation()
  }
}

function updateComplexity() {
  generatePattern()
}

function generatePattern() {
  if (!sacredCanvas.value) return
  
  const canvas = sacredCanvas.value
  const ctx = canvas.getContext('2d')
  if (!ctx) return
  
  // Clear canvas
  ctx.clearRect(0, 0, canvas.width, canvas.height)
  
  // Set up drawing context
  ctx.strokeStyle = selectedColor.value
  ctx.lineWidth = 2
  ctx.globalAlpha = 0.8
  
  const centerX = canvas.width / 2
  const centerY = canvas.height / 2
  const radius = 20 + (complexity.value * 15)
  
  // Draw pattern based on selection
  switch (selectedPattern.value) {
    case 'flower-of-life':
      drawFlowerOfLife(ctx, centerX, centerY, radius)
      break
    case 'metatron-cube':
      drawMetatronsCube(ctx, centerX, centerY, radius)
      break
    case 'golden-spiral':
      drawGoldenSpiral(ctx, centerX, centerY, radius)
      break
    case 'merkaba':
      drawMerkaba(ctx, centerX, centerY, radius)
      break
    case 'sri-yantra':
      drawSriYantra(ctx, centerX, centerY, radius)
      break
    default:
      drawFlowerOfLife(ctx, centerX, centerY, radius)
  }
}

function drawFlowerOfLife(ctx: CanvasRenderingContext2D, centerX: number, centerY: number, radius: number) {
  const angles = [0, 60, 120, 180, 240, 300]
  
  // Center circle
  ctx.beginPath()
  ctx.arc(centerX, centerY, radius, 0, 2 * Math.PI)
  ctx.stroke()
  
  // Surrounding circles
  for (let layer = 1; layer <= complexity.value; layer++) {
    for (const angle of angles) {
      const radian = (angle * Math.PI) / 180
      const x = centerX + (Math.cos(radian) * radius * layer * 1.5)
      const y = centerY + (Math.sin(radian) * radius * layer * 1.5)
      
      ctx.beginPath()
      ctx.arc(x, y, radius, 0, 2 * Math.PI)
      ctx.stroke()
    }
  }
}

function drawMetatronsCube(ctx: CanvasRenderingContext2D, centerX: number, centerY: number, radius: number) {
  const points = []
  const numPoints = 13
  
  // Generate points
  for (let i = 0; i < numPoints; i++) {
    const angle = (i * 2 * Math.PI) / numPoints
    const r = i === 0 ? 0 : radius * (1 + Math.floor(i / 6) * 0.5)
    points.push({
      x: centerX + Math.cos(angle) * r,
      y: centerY + Math.sin(angle) * r
    })
  }
  
  // Draw connections
  for (let i = 0; i < points.length; i++) {
    for (let j = i + 1; j < points.length; j++) {
      ctx.beginPath()
      ctx.moveTo(points[i].x, points[i].y)
      ctx.lineTo(points[j].x, points[j].y)
      ctx.stroke()
    }
  }
}

function drawGoldenSpiral(ctx: CanvasRenderingContext2D, centerX: number, centerY: number, radius: number) {
  const phi = (1 + Math.sqrt(5)) / 2
  const steps = complexity.value * 50
  
  ctx.beginPath()
  let currentRadius = radius / 10
  let angle = 0
  
  for (let i = 0; i < steps; i++) {
    const x = centerX + Math.cos(angle) * currentRadius
    const y = centerY + Math.sin(angle) * currentRadius
    
    if (i === 0) {
      ctx.moveTo(x, y)
    } else {
      ctx.lineTo(x, y)
    }
    
    angle += 0.1
    currentRadius *= Math.pow(phi, 0.01)
  }
  
  ctx.stroke()
}

function drawMerkaba(ctx: CanvasRenderingContext2D, centerX: number, centerY: number, radius: number) {
  // Draw two intersecting tetrahedra
  const height = radius * Math.sqrt(3) / 2
  
  // First tetrahedron (pointing up)
  ctx.beginPath()
  ctx.moveTo(centerX, centerY - height)
  ctx.lineTo(centerX - radius, centerY + height / 2)
  ctx.lineTo(centerX + radius, centerY + height / 2)
  ctx.closePath()
  ctx.stroke()
  
  // Second tetrahedron (pointing down)
  ctx.beginPath()
  ctx.moveTo(centerX, centerY + height)
  ctx.lineTo(centerX - radius, centerY - height / 2)
  ctx.lineTo(centerX + radius, centerY - height / 2)
  ctx.closePath()
  ctx.stroke()
  
  // Add inner geometry based on complexity
  for (let i = 1; i < complexity.value; i++) {
    const scale = 1 - (i * 0.15)
    const scaledRadius = radius * scale
    const scaledHeight = height * scale
    
    ctx.globalAlpha = 0.6 - (i * 0.1)
    
    // Scaled triangles
    ctx.beginPath()
    ctx.moveTo(centerX, centerY - scaledHeight)
    ctx.lineTo(centerX - scaledRadius, centerY + scaledHeight / 2)
    ctx.lineTo(centerX + scaledRadius, centerY + scaledHeight / 2)
    ctx.closePath()
    ctx.stroke()
    
    ctx.beginPath()
    ctx.moveTo(centerX, centerY + scaledHeight)
    ctx.lineTo(centerX - scaledRadius, centerY - scaledHeight / 2)
    ctx.lineTo(centerX + scaledRadius, centerY - scaledHeight / 2)
    ctx.closePath()
    ctx.stroke()
  }
}

function drawSriYantra(ctx: CanvasRenderingContext2D, centerX: number, centerY: number, radius: number) {
  // Outer circle
  ctx.beginPath()
  ctx.arc(centerX, centerY, radius * 1.5, 0, 2 * Math.PI)
  ctx.stroke()
  
  // Draw interlocking triangles
  const triangleSize = radius * 0.8
  
  // Upward triangles
  for (let i = 0; i < 5; i++) {
    const scale = 1 - (i * 0.15)
    const size = triangleSize * scale
    const height = size * Math.sqrt(3) / 2
    
    ctx.beginPath()
    ctx.moveTo(centerX, centerY - height)
    ctx.lineTo(centerX - size, centerY + height / 2)
    ctx.lineTo(centerX + size, centerY + height / 2)
    ctx.closePath()
    ctx.stroke()
  }
  
  // Downward triangles
  for (let i = 0; i < 4; i++) {
    const scale = 0.9 - (i * 0.15)
    const size = triangleSize * scale
    const height = size * Math.sqrt(3) / 2
    
    ctx.beginPath()
    ctx.moveTo(centerX, centerY + height)
    ctx.lineTo(centerX - size, centerY - height / 2)
    ctx.lineTo(centerX + size, centerY - height / 2)
    ctx.closePath()
    ctx.stroke()
  }
  
  // Center point
  ctx.beginPath()
  ctx.arc(centerX, centerY, 3, 0, 2 * Math.PI)
  ctx.fill()
}

function animatePattern() {
  if (isAnimating.value) {
    stopAnimation()
  } else {
    startAnimation()
  }
}

function startAnimation() {
  isAnimating.value = true
  let rotation = 0
  
  function animate() {
    if (!isAnimating.value || !sacredCanvas.value) return
    
    const canvas = sacredCanvas.value
    const ctx = canvas.getContext('2d')
    if (!ctx) return
    
    // Clear and save context
    ctx.clearRect(0, 0, canvas.width, canvas.height)
    ctx.save()
    
    // Apply rotation
    ctx.translate(canvas.width / 2, canvas.height / 2)
    ctx.rotate(rotation)
    ctx.translate(-canvas.width / 2, -canvas.height / 2)
    
    // Generate pattern
    generatePattern()
    
    // Restore context
    ctx.restore()
    
    rotation += animationSpeed.value * 0.01
    animationId.value = requestAnimationFrame(animate)
  }
  
  animate()
}

function stopAnimation() {
  isAnimating.value = false
  if (animationId.value) {
    cancelAnimationFrame(animationId.value)
  }
  generatePattern() // Redraw static pattern
}

function clearCanvas() {
  if (!sacredCanvas.value) return
  
  const canvas = sacredCanvas.value
  const ctx = canvas.getContext('2d')
  if (!ctx) return
  
  ctx.clearRect(0, 0, canvas.width, canvas.height)
}

function savePattern() {
  console.log('💾 Saving pattern:', currentPattern.value.name)
  // In real app, save to user's collection
}

function sharePattern() {
  console.log('🔗 Sharing pattern:', currentPattern.value.name)
  // In real app, generate share link
}

function loadPattern(pattern: SacredPattern) {
  selectedPattern.value = pattern.name.toLowerCase().replace(/\s+/g, '-').replace(/'/g, '')
  frequency.value = pattern.frequency
  complexity.value = pattern.complexity
  changePattern()
  console.log('📁 Loaded pattern:', pattern.name)
}

function isFavorite(pattern: SacredPattern): boolean {
  // In real app, check user's favorites
  return pattern.id === '1' || pattern.id === '3'
}

function toggleFavorite(pattern: SacredPattern) {
  console.log('⭐ Toggling favorite:', pattern.name)
  // In real app, update user's favorites
}

function createNewPattern() {
  console.log('➕ Creating new pattern')
  // In real app, open pattern creation modal
}

function toggleMeditation() {
  if (isMeditating.value) {
    endMeditation()
  } else {
    startMeditation()
  }
}

function startMeditation() {
  isMeditating.value = true
  meditationTime.value = 0
  
  meditationInterval.value = setInterval(() => {
    meditationTime.value++
    
    // Breathing guide (4-7-8 pattern)
    const cycle = meditationTime.value % 19
    if (cycle < 4) {
      breathPhase.value = 'inhale'
      breathInstruction.value = 'Breathe in...'
    } else if (cycle < 11) {
      breathPhase.value = 'inhale'
      breathInstruction.value = 'Hold...'
    } else {
      breathPhase.value = 'exhale'
      breathInstruction.value = 'Breathe out...'
    }
  }, 1000)
}

function endMeditation() {
  isMeditating.value = false
  if (meditationInterval.value) {
    clearInterval(meditationInterval.value)
  }
}

function formatTime(seconds: number): string {
  const mins = Math.floor(seconds / 60)
  const secs = seconds % 60
  return `${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`
}

function formatDate(dateString: string): string {
  const date = new Date(dateString)
  return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric' })
}

function resonateWithShowcase(showcase: PatternShowcase) {
  console.log('💫 Resonating with:', showcase.title)
  showcase.resonanceCount++
}

function commentOnShowcase(showcase: PatternShowcase) {
  console.log('💬 Commenting on:', showcase.title)
  router.push(`/community/showcase/${showcase.id}`)
}

// Drawing functions
function startDrawing(event: MouseEvent) {
  isDrawing.value = true
  draw(event)
}

function draw(event: MouseEvent) {
  if (!isDrawing.value || !sacredCanvas.value) return
  
  const canvas = sacredCanvas.value
  const ctx = canvas.getContext('2d')
  if (!ctx) return
  
  const rect = canvas.getBoundingClientRect()
  const x = event.clientX - rect.left
  const y = event.clientY - rect.top
  
  ctx.strokeStyle = selectedColor.value
  ctx.lineWidth = 3
  ctx.lineTo(x, y)
  ctx.stroke()
  ctx.beginPath()
  ctx.moveTo(x, y)
}

function stopDrawing() {
  isDrawing.value = false
}

onMounted(() => {
  console.log('⚡ Sacred Geometry Laboratory activated')
  initializeCanvas()
  consciousnessStore.loadUserData()
})

onUnmounted(() => {
  if (animationId.value) {
    cancelAnimationFrame(animationId.value)
  }
  if (meditationInterval.value) {
    clearInterval(meditationInterval.value)
  }
})
</script>

<style scoped lang="scss">
.sacred-geometry-lab {
  position: relative;
  min-height: 100vh;
  background: linear-gradient(135deg, #0a0a1a 0%, #1a0a2a 50%, #0a1a2a 100%);
}

.lab-container {
  position: relative;
  z-index: 2;
  max-width: 1600px;
  margin: 0 auto;
  padding: 2rem;
}

.lab-header {
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
  
  .lab-description {
    font-size: 1.2rem;
    color: var(--sacred-light);
    opacity: 0.9;
    margin-bottom: 2rem;
  }
  
  .lab-stats {
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

.sacred-live-viz {
  margin-bottom: 3rem;
  background: rgba(255, 255, 255, 0.02);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.05);
  border-radius: 20px;
  padding: 2rem;
}

.canvas-workspace {
  display: grid;
  grid-template-columns: 1fr 400px;
  gap: 3rem;
  margin-bottom: 4rem;
  
  .canvas-container {
    position: relative;
    
    .sacred-canvas {
      width: 100%;
      max-width: 600px;
      height: 600px;
      background: rgba(255, 255, 255, 0.02);
      border: 2px solid rgba(255, 255, 255, 0.1);
      border-radius: 20px;
      cursor: crosshair;
    }
    
    .canvas-overlay {
      position: absolute;
      top: 1rem;
      left: 1rem;
      background: rgba(0, 0, 0, 0.7);
      padding: 0.75rem 1rem;
      border-radius: 12px;
      
      .canvas-info {
        display: flex;
        flex-direction: column;
        gap: 0.25rem;
        
        .pattern-name {
          color: var(--sacred-gold);
          font-weight: 600;
        }
        
        .pattern-frequency {
          color: var(--sacred-blue);
          font-size: 0.9rem;
        }
      }
    }
  }
  
  .canvas-controls {
    .tool-palette {
      background: rgba(255, 255, 255, 0.05);
      backdrop-filter: blur(20px);
      border: 1px solid rgba(255, 255, 255, 0.1);
      border-radius: 20px;
      padding: 2rem;
      margin-bottom: 2rem;
      
      h3 {
        color: var(--sacred-gold);
        margin-bottom: 1.5rem;
      }
      
      .tool-group {
        margin-bottom: 1.5rem;
        
        label {
          display: block;
          color: var(--sacred-light);
          margin-bottom: 0.5rem;
          font-weight: 600;
        }
        
        select,
        input[type="range"] {
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
        
        .color-palette {
          display: grid;
          grid-template-columns: repeat(4, 1fr);
          gap: 0.5rem;
          
          .color-swatch {
            width: 40px;
            height: 40px;
            border-radius: 8px;
            cursor: pointer;
            border: 2px solid transparent;
            transition: all 0.3s ease;
            
            &:hover,
            &.active {
              border-color: var(--sacred-gold);
              transform: scale(1.1);
            }
          }
        }
      }
    }
    
    .action-buttons {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 1rem;
      
      .action-btn {
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 0.5rem;
        padding: 1rem;
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
        
        &.quaternary {
          background: transparent;
          color: var(--sacred-green);
          border-color: var(--sacred-green);
          
          &:hover {
            background: var(--sacred-green);
            color: var(--sacred-light);
          }
        }
      }
    }
  }
}

.library-filters {
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
  
  .create-pattern-btn {
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

.patterns-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 2rem;
  margin-bottom: 4rem;
}

.pattern-card {
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 20px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;
  
  &:hover {
    transform: translateY(-5px);
    border-color: var(--sacred-gold);
  }
  
  .pattern-preview {
    .pattern-image {
      height: 200px;
      background-size: cover;
      background-position: center;
      position: relative;
      
      .pattern-overlay {
        position: absolute;
        top: 1rem;
        right: 1rem;
        background: rgba(0, 0, 0, 0.7);
        padding: 0.5rem 0.75rem;
        border-radius: 8px;
        
        .pattern-frequency {
          color: var(--sacred-blue);
          font-size: 0.8rem;
          font-weight: 600;
        }
      }
    }
  }
  
  .pattern-info {
    padding: 1.5rem;
    
    .pattern-name {
      color: var(--sacred-light);
      margin-bottom: 0.5rem;
      font-size: 1.1rem;
    }
    
    .pattern-description {
      color: var(--sacred-light);
      opacity: 0.8;
      font-size: 0.9rem;
      line-height: 1.4;
      margin-bottom: 1rem;
    }
    
    .pattern-meta {
      display: flex;
      gap: 1rem;
      margin-bottom: 1rem;
      
      .pattern-category,
      .pattern-complexity {
        padding: 0.25rem 0.75rem;
        background: rgba(255, 255, 255, 0.1);
        border-radius: 12px;
        font-size: 0.8rem;
        color: var(--sacred-blue);
      }
    }
    
    .pattern-stats {
      display: flex;
      gap: 1rem;
      margin-bottom: 1.5rem;
      
      .stat {
        display: flex;
        align-items: center;
        gap: 0.25rem;
        font-size: 0.9rem;
        
        .stat-icon {
          font-size: 0.8rem;
        }
        
        .stat-value {
          color: var(--sacred-light);
        }
      }
    }
    
    .pattern-actions {
      display: flex;
      gap: 1rem;
      align-items: center;
      
      .load-btn {
        flex: 1;
        padding: 0.75rem;
        background: linear-gradient(45deg, var(--sacred-blue), var(--sacred-purple));
        color: var(--sacred-light);
        border: none;
        border-radius: 8px;
        font-weight: 600;
        cursor: pointer;
        transition: all 0.3s ease;
        
        &:hover {
          transform: translateY(-1px);
        }
      }
      
      .favorite-btn {
        padding: 0.75rem;
        background: transparent;
        color: var(--sacred-light);
        border: 1px solid rgba(255, 255, 255, 0.3);
        border-radius: 8px;
        cursor: pointer;
        transition: all 0.3s ease;
        
        &.active {
          color: var(--sacred-gold);
          border-color: var(--sacred-gold);
        }
        
        &:hover {
          background: rgba(255, 255, 255, 0.1);
        }
      }
    }
  }
}

.integration-workspace {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 3rem;
  margin-bottom: 4rem;
  
  .meditation-space,
  .frequency-analysis {
    background: rgba(255, 255, 255, 0.05);
    backdrop-filter: blur(20px);
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 20px;
    padding: 2rem;
    
    h3 {
      color: var(--sacred-gold);
      margin-bottom: 1rem;
    }
    
    p {
      color: var(--sacred-light);
      opacity: 0.8;
      margin-bottom: 2rem;
    }
  }
  
  .meditation-controls {
    .meditation-btn {
      width: 100%;
      padding: 1rem 2rem;
      background: linear-gradient(45deg, var(--sacred-purple), var(--sacred-blue));
      color: var(--sacred-light);
      border: none;
      border-radius: 12px;
      font-weight: 600;
      cursor: pointer;
      transition: all 0.3s ease;
      margin-bottom: 2rem;
      
      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 8px 25px rgba(138, 43, 226, 0.3);
      }
      
      &.active {
        background: linear-gradient(45deg, var(--sacred-red), var(--sacred-orange));
      }
    }
    
    .meditation-timer {
      text-align: center;
      
      .timer-display {
        font-size: 2rem;
        color: var(--sacred-gold);
        font-weight: 700;
        margin-bottom: 1rem;
      }
      
      .breathing-guide {
        .breath-circle {
          width: 80px;
          height: 80px;
          border: 3px solid var(--sacred-blue);
          border-radius: 50%;
          margin: 0 auto 1rem;
          transition: all 0.5s ease;
          
          &.inhale {
            transform: scale(1.2);
            border-color: var(--sacred-gold);
          }
        }
        
        .breath-instruction {
          color: var(--sacred-light);
          font-style: italic;
        }
      }
    }
  }
  
  .frequency-display {
    .frequency-canvas {
      width: 100%;
      height: 150px;
      background: rgba(255, 255, 255, 0.02);
      border: 1px solid rgba(255, 255, 255, 0.1);
      border-radius: 8px;
      margin-bottom: 1rem;
    }
  }
  
  .harmony-metrics {
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
}

.showcase-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: 2rem;
}

.showcase-card {
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 20px;
  padding: 2rem;
  
  .showcase-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 1.5rem;
    
    .creator-info {
      display: flex;
      align-items: center;
      gap: 1rem;
      
      .creator-avatar {
        width: 50px;
        height: 50px;
        border-radius: 50%;
        object-fit: cover;
      }
      
      .creator-details {
        .creator-name {
          display: block;
          color: var(--sacred-light);
          font-weight: 600;
        }
        
        .creator-level {
          color: var(--sacred-blue);
          font-size: 0.9rem;
        }
      }
    }
    
    .showcase-date {
      color: var(--sacred-light);
      opacity: 0.7;
      font-size: 0.9rem;
    }
  }
  
  .showcase-content {
    .pattern-showcase {
      height: 200px;
      border-radius: 12px;
      overflow: hidden;
      margin-bottom: 1.5rem;
      
      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }
    }
    
    .showcase-title {
      color: var(--sacred-gold);
      margin-bottom: 1rem;
    }
    
    .showcase-description {
      color: var(--sacred-light);
      opacity: 0.8;
      margin-bottom: 1.5rem;
      line-height: 1.5;
    }
    
    .showcase-insights {
      background: rgba(255, 255, 255, 0.05);
      padding: 1rem;
      border-radius: 12px;
      margin-bottom: 1.5rem;
      
      h4 {
        color: var(--sacred-purple);
        margin-bottom: 0.5rem;
        font-size: 0.9rem;
      }
      
      p {
        color: var(--sacred-light);
        font-style: italic;
        line-height: 1.4;
      }
    }
  }
  
  .showcase-interaction {
    .showcase-stats {
      display: flex;
      gap: 1.5rem;
      margin-bottom: 1rem;
      
      .stat {
        display: flex;
        align-items: center;
        gap: 0.5rem;
        
        .stat-icon {
          font-size: 1.1rem;
        }
        
        .stat-value {
          color: var(--sacred-light);
          font-weight: 600;
        }
      }
    }
    
    .showcase-actions {
      display: flex;
      gap: 1rem;
      
      .resonate-btn,
      .comment-btn {
        flex: 1;
        padding: 0.75rem 1rem;
        border-radius: 8px;
        font-weight: 600;
        cursor: pointer;
        transition: all 0.3s ease;
        border: none;
        
        &:hover {
          transform: translateY(-1px);
        }
      }
      
      .resonate-btn {
        background: linear-gradient(45deg, var(--sacred-purple), var(--sacred-blue));
        color: var(--sacred-light);
      }
      
      .comment-btn {
        background: transparent;
        color: var(--sacred-gold);
        border: 1px solid var(--sacred-gold);
        
        &:hover {
          background: var(--sacred-gold);
          color: var(--sacred-dark);
        }
      }
    }
  }
}

@keyframes gradientShift {
  0%, 100% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
}

@media (max-width: 1200px) {
  .canvas-workspace {
    grid-template-columns: 1fr;
    
    .canvas-container {
      .sacred-canvas {
        max-width: 100%;
        height: 400px;
      }
    }
  }
  
  .integration-workspace {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .lab-stats {
    flex-direction: column;
    align-items: center;
    gap: 1rem !important;
  }
  
  .library-filters {
    flex-direction: column;
    align-items: stretch;
    
    .create-pattern-btn {
      margin-left: 0;
    }
  }
  
  .patterns-grid {
    grid-template-columns: 1fr;
  }
  
  .showcase-grid {
    grid-template-columns: 1fr;
  }
  
  .canvas-controls .action-buttons {
    grid-template-columns: 1fr;
  }
}
</style>
