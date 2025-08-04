<template>
  <div class="sacred-geometry-viz">
    <div class="viz-header">
      <h2>🔯 Sacred Geometry Live Visualization</h2>
      <div class="controls">
        <button @click="toggleAnimation" class="control-btn">
          {{ isAnimating ? '⏸️ Pause' : '▶️ Play' }}
        </button>
        <button @click="regenerateGeometry" class="control-btn">
          🔄 Regenerate
        </button>
        <button @click="fetchBackendData" class="control-btn">
          📡 Sync Backend
        </button>
      </div>
    </div>
    
    <div class="geometry-container">
      <div ref="p5Container" class="p5-canvas"></div>
    </div>
    
    <div class="geometry-info" v-if="geometryData">
      <h3>Current Sacred Patterns</h3>
      <div class="info-grid">
        <div class="info-item">
          <span class="label">Golden Ratio Alignment:</span>
          <span class="value">{{ geometryData.goldenRatioAlignment?.toFixed(2) }}%</span>
        </div>
        <div class="info-item">
          <span class="label">Fibonacci Harmony:</span>
          <span class="value">{{ geometryData.fibonacciHarmony?.toFixed(2) }}%</span>
        </div>
        <div class="info-item">
          <span class="label">Flower of Life Resonance:</span>
          <span class="value">{{ geometryData.flowerOfLifeResonance?.toFixed(2) }}%</span>
        </div>
        <div class="info-item">
          <span class="label">Merkaba Energy:</span>
          <span class="value">{{ geometryData.merkabaEnergy?.toFixed(2) }}%</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import p5 from 'p5'

// State
const p5Container = ref<HTMLElement>()
const isAnimating = ref(true)
const geometryData = ref<any>(null)
let p5Instance: p5 | null = null

// Sacred Geometry Data
const sacredRatios = {
  goldenRatio: 1.618033988749,
  phi: 1.618033988749,
  sqrt2: 1.414213562373,
  sqrt3: 1.732050807569,
  sqrt5: 2.236067977499
}

// Initialize P5.js
onMounted(() => {
  if (p5Container.value) {
    p5Instance = new p5(sketch, p5Container.value)
  }
  fetchBackendData()
})

onUnmounted(() => {
  if (p5Instance) {
    p5Instance.remove()
  }
})

// P5.js Sketch
function sketch(p: p5) {
  let time = 0
  let goldenAngle = 137.5 * (Math.PI / 180) // Golden angle in radians
  
  p.setup = () => {
    p.createCanvas(800, 600)
    p.colorMode(p.HSB, 360, 100, 100, 100)
  }
  
  p.draw = () => {
    if (!isAnimating.value) return
    
    // Sacred background
    p.background(240, 20, 5, 80)
    p.translate(p.width / 2, p.height / 2)
    
    time += 0.01
    
    // Draw Flower of Life pattern
    drawFlowerOfLife(p, time)
    
    // Draw Golden Spiral
    drawGoldenSpiral(p, time)
    
    // Draw Fibonacci Pattern
    drawFibonacciPattern(p, time)
    
    // Draw Merkaba
    drawMerkaba(p, time)
  }
}

function drawFlowerOfLife(p: p5, time: number) {
  p.push()
  p.stroke(60, 80, 90, 60)
  p.strokeWeight(2)
  p.noFill()
  
  const radius = 50 + Math.sin(time * 2) * 10
  const numCircles = 19
  
  for (let i = 0; i < numCircles; i++) {
    const angle = (i / numCircles) * Math.PI * 2 * 3
    const x = Math.cos(angle) * radius * 1.5
    const y = Math.sin(angle) * radius * 1.5
    
    p.circle(x, y, radius * 2)
  }
  p.pop()
}

function drawGoldenSpiral(p: p5, time: number) {
  p.push()
  p.stroke(280, 70, 85, 70)
  p.strokeWeight(3)
  p.noFill()
  
  p.rotate(time * 0.5)
  
  const spiralTurns = 6
  const maxRadius = 200
  
  p.beginShape()
  for (let i = 0; i < spiralTurns * 360; i += 2) {
    const angle = i * (Math.PI / 180)
    const radius = (i / (spiralTurns * 360)) * maxRadius
    const goldenRadius = radius * Math.pow(sacredRatios.goldenRatio, angle / (2 * Math.PI))
    
    const x = Math.cos(angle) * goldenRadius
    const y = Math.sin(angle) * goldenRadius
    
    p.vertex(x, y)
  }
  p.endShape()
  p.pop()
}

function drawFibonacciPattern(p: p5, time: number) {
  p.push()
  const fibonacci = [1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89]
  
  for (let i = 0; i < fibonacci.length; i++) {
    const angle = i * goldenAngle + time * 0.3
    const radius = fibonacci[i] * 3
    
    const x = Math.cos(angle) * radius
    const y = Math.sin(angle) * radius
    
    p.fill(120 + i * 20, 70, 90, 70)
    p.noStroke()
    p.circle(x, y, fibonacci[i] * 2)
  }
  p.pop()
}

function drawMerkaba(p: p5, time: number) {
  p.push()
  p.stroke(320, 90, 95, 80)
  p.strokeWeight(2)
  p.noFill()
  
  p.rotateX(time * 0.4)
  p.rotateY(time * 0.3)
  p.rotateZ(time * 0.2)
  
  const size = 100
  
  // Upper tetrahedron
  p.beginShape(p.TRIANGLES)
  p.vertex(0, -size, 0)
  p.vertex(-size, size, -size)
  p.vertex(size, size, -size)
  
  p.vertex(0, -size, 0)
  p.vertex(size, size, -size)
  p.vertex(size, size, size)
  
  p.vertex(0, -size, 0)
  p.vertex(size, size, size)
  p.vertex(-size, size, size)
  
  p.vertex(0, -size, 0)
  p.vertex(-size, size, size)
  p.vertex(-size, size, -size)
  p.endShape()
  
  // Lower tetrahedron (inverted)
  p.beginShape(p.TRIANGLES)
  p.vertex(0, size, 0)
  p.vertex(-size, -size, size)
  p.vertex(size, -size, size)
  
  p.vertex(0, size, 0)
  p.vertex(size, -size, size)
  p.vertex(size, -size, -size)
  
  p.vertex(0, size, 0)
  p.vertex(size, -size, -size)
  p.vertex(-size, -size, -size)
  
  p.vertex(0, size, 0)
  p.vertex(-size, -size, -size)
  p.vertex(-size, -size, size)
  p.endShape()
  
  p.pop()
}

// Control Functions
function toggleAnimation() {
  isAnimating.value = !isAnimating.value
}

function regenerateGeometry() {
  if (p5Instance) {
    // Reset and regenerate pattern
    time = 0
  }
}

async function fetchBackendData() {
  try {
    const response = await fetch('http://localhost:8081/api/v1/consciousness/sacred-geometry/sacred-user', {
      method: 'GET',
      headers: { 'Accept': 'application/json' }
    })
    
    if (response.ok) {
      const data = await response.json()
      geometryData.value = data.geometryFactors
      console.log('🔯 Sacred Geometry data from backend:', data)
    }
  } catch (error) {
    console.error('Failed to fetch geometry data:', error)
    // Use mock data for demo
    geometryData.value = {
      goldenRatioAlignment: 78.5,
      fibonacciHarmony: 82.3,
      flowerOfLifeResonance: 91.2,
      merkabaEnergy: 76.8
    }
  }
}
</script>

<style scoped lang="scss">
@use '@/styles/main.scss' as *;

.sacred-geometry-viz {
  padding: var(--spacing-lg);
  
  .viz-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: var(--spacing-lg);
    
    h2 {
      color: var(--sacred-primary);
      margin: 0;
    }
    
    .controls {
      display: flex;
      gap: var(--spacing-sm);
      
      .control-btn {
        padding: var(--spacing-sm) var(--spacing-md);
        background: var(--sacred-primary);
        color: white;
        border: none;
        border-radius: var(--border-radius-md);
        cursor: pointer;
        font-weight: 500;
        transition: all 0.3s ease;
        
        &:hover {
          background: var(--sacred-secondary);
          transform: translateY(-2px);
        }
      }
    }
  }
  
  .geometry-container {
    display: flex;
    justify-content: center;
    margin-bottom: var(--spacing-lg);
    
    .p5-canvas {
      border-radius: var(--border-radius-lg);
      overflow: hidden;
      box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
      background: linear-gradient(135deg, #1a1a2e, #16213e);
    }
  }
  
  .geometry-info {
    background: rgba(var(--sacred-primary-rgb), 0.1);
    padding: var(--spacing-lg);
    border-radius: var(--border-radius-lg);
    border: 1px solid rgba(var(--sacred-primary-rgb), 0.2);
    
    h3 {
      margin-bottom: var(--spacing-md);
      color: var(--sacred-primary);
    }
    
    .info-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: var(--spacing-md);
      
      .info-item {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: var(--spacing-sm);
        background: rgba(255, 255, 255, 0.05);
        border-radius: var(--border-radius-md);
        
        .label {
          font-weight: 500;
          color: var(--text-secondary);
        }
        
        .value {
          font-weight: 700;
          color: var(--sacred-gold);
        }
      }
    }
  }
}
</style>
