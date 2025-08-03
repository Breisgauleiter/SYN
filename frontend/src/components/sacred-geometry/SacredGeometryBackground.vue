<template>
  <div class="sacred-geometry-background">
    <canvas ref="geometryCanvas" class="geometry-canvas"></canvas>
    <div class="sacred-particles" ref="particlesContainer"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import p5 from 'p5'

const geometryCanvas = ref<HTMLCanvasElement>()
const particlesContainer = ref<HTMLDivElement>()
let p5Instance: p5 | null = null

onMounted(() => {
  initSacredGeometry()
  createSacredParticles()
})

onUnmounted(() => {
  if (p5Instance) {
    p5Instance.remove()
  }
})

function initSacredGeometry() {
  if (!geometryCanvas.value) return

  const sketch = (p: p5) => {
    let time = 0
    let goldenRatio = 1.618033988749895
    let patterns: any[] = []

    p.setup = () => {
      p.createCanvas(window.innerWidth, window.innerHeight)
      p.colorMode(p.HSB, 360, 100, 100, 100)
      
      // Initialize sacred geometry patterns
      for (let i = 0; i < 5; i++) {
        patterns.push({
          x: p.random(p.width),
          y: p.random(p.height),
          size: p.random(50, 200),
          rotation: 0,
          speed: p.random(0.002, 0.01),
          type: Math.floor(p.random(3))
        })
      }
    }

    p.draw = () => {
      // Cosmic background
      p.background(240, 80, 5, 20)
      
      time += 0.01
      
      // Draw sacred geometry patterns
      patterns.forEach((pattern, index) => {
        p.push()
        p.translate(pattern.x, pattern.y)
        p.rotate(pattern.rotation)
        
        pattern.rotation += pattern.speed
        
        // Golden ratio spiral
        if (pattern.type === 0) {
          drawGoldenSpiral(p, pattern.size, time + index)
        }
        // Flower of Life
        else if (pattern.type === 1) {
          drawFlowerOfLife(p, pattern.size, time + index)
        }
        // Merkaba
        else {
          drawMerkaba(p, pattern.size, time + index)
        }
        
        p.pop()
      })
      
      // Slowly move patterns
      patterns.forEach(pattern => {
        pattern.x += Math.sin(time + pattern.speed * 100) * 0.5
        pattern.y += Math.cos(time + pattern.speed * 80) * 0.3
        
        // Wrap around screen
        if (pattern.x > p.width + 100) pattern.x = -100
        if (pattern.x < -100) pattern.x = p.width + 100
        if (pattern.y > p.height + 100) pattern.y = -100
        if (pattern.y < -100) pattern.y = p.height + 100
      })
    }

    p.windowResized = () => {
      p.resizeCanvas(window.innerWidth, window.innerHeight)
    }

    function drawGoldenSpiral(p: p5, size: number, timeOffset: number) {
      p.stroke(45 + Math.sin(timeOffset) * 15, 70, 80, 30)
      p.strokeWeight(2)
      p.noFill()
      
      let angle = 0
      let radius = 1
      
      p.beginShape()
      for (let i = 0; i < 100; i++) {
        let x = radius * Math.cos(angle)
        let y = radius * Math.sin(angle)
        p.vertex(x, y)
        
        angle += 0.1
        radius *= 1.02
        
        if (radius > size / 2) break
      }
      p.endShape()
    }

    function drawFlowerOfLife(p: p5, size: number, timeOffset: number) {
      let hue = (180 + Math.sin(timeOffset) * 30) % 360
      p.stroke(hue, 60, 90, 25)
      p.strokeWeight(1.5)
      p.noFill()
      
      let radius = size / 6
      let centers = []
      
      // Create flower of life pattern
      for (let ring = 0; ring <= 2; ring++) {
        if (ring === 0) {
          centers.push({ x: 0, y: 0 })
        } else {
          for (let i = 0; i < 6 * ring; i++) {
            let angle = (i / (6 * ring)) * p.TWO_PI
            let distance = ring * radius * Math.sqrt(3)
            centers.push({
              x: distance * Math.cos(angle),
              y: distance * Math.sin(angle)
            })
          }
        }
      }
      
      // Draw circles
      centers.forEach(center => {
        p.circle(center.x, center.y, radius * 2)
      })
    }

    function drawMerkaba(p: p5, size: number, timeOffset: number) {
      let hue = (300 + Math.sin(timeOffset) * 40) % 360
      p.stroke(hue, 80, 95, 20)
      p.strokeWeight(1)
      p.noFill()
      
      let radius = size / 3
      
      // Upper tetrahedron
      p.push()
      p.rotateX(timeOffset * 0.5)
      p.rotateY(timeOffset * 0.3)
      
      for (let i = 0; i < 3; i++) {
        let angle1 = (i / 3) * p.TWO_PI
        let angle2 = ((i + 1) / 3) * p.TWO_PI
        
        let x1 = radius * Math.cos(angle1)
        let y1 = radius * Math.sin(angle1)
        let x2 = radius * Math.cos(angle2)
        let y2 = radius * Math.sin(angle2)
        
        // Draw triangle edges
        p.line(x1, y1, x2, y2)
        p.line(x1, y1, 0, -radius)
        p.line(x2, y2, 0, -radius)
      }
      p.pop()
      
      // Lower tetrahedron (inverted)
      p.push()
      p.rotateX(-timeOffset * 0.5)
      p.rotateY(-timeOffset * 0.3)
      
      for (let i = 0; i < 3; i++) {
        let angle1 = (i / 3) * p.TWO_PI + p.PI / 3
        let angle2 = ((i + 1) / 3) * p.TWO_PI + p.PI / 3
        
        let x1 = radius * Math.cos(angle1)
        let y1 = radius * Math.sin(angle1)
        let x2 = radius * Math.cos(angle2)
        let y2 = radius * Math.sin(angle2)
        
        p.line(x1, y1, x2, y2)
        p.line(x1, y1, 0, radius)
        p.line(x2, y2, 0, radius)
      }
      p.pop()
    }
  }

  p5Instance = new p5(sketch, geometryCanvas.value)
}

function createSacredParticles() {
  if (!particlesContainer.value) return

  for (let i = 0; i < 50; i++) {
    const particle = document.createElement('div')
    particle.className = 'sacred-particle'
    
    // Random position
    particle.style.left = Math.random() * 100 + '%'
    particle.style.top = Math.random() * 100 + '%'
    
    // Random animation delay
    particle.style.animationDelay = Math.random() * 20 + 's'
    particle.style.animationDuration = (15 + Math.random() * 10) + 's'
    
    // Random size
    const size = 2 + Math.random() * 4
    particle.style.width = size + 'px'
    particle.style.height = size + 'px'
    
    particlesContainer.value?.appendChild(particle)
  }
}
</script>

<style scoped lang="scss">
.sacred-geometry-background {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
  overflow: hidden;
  pointer-events: none;
}

.geometry-canvas {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0.6;
}

.sacred-particles {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  
  .sacred-particle {
    position: absolute;
    background: radial-gradient(circle, var(--sacred-gold) 0%, transparent 70%);
    border-radius: 50%;
    opacity: 0.7;
    animation: sacredFloat infinite ease-in-out;
    filter: blur(0.5px);
  }
}

@keyframes sacredFloat {
  0%, 100% {
    transform: translateY(0px) rotate(0deg);
    opacity: 0.7;
  }
  25% {
    transform: translateY(-20px) rotate(90deg);
    opacity: 1;
  }
  50% {
    transform: translateY(-10px) rotate(180deg);
    opacity: 0.5;
  }
  75% {
    transform: translateY(-30px) rotate(270deg);
    opacity: 0.8;
  }
}

/* Add some variety to particles */
.sacred-particle:nth-child(odd) {
  background: radial-gradient(circle, var(--sacred-blue) 0%, transparent 70%);
  animation-direction: reverse;
}

.sacred-particle:nth-child(3n) {
  background: radial-gradient(circle, var(--sacred-purple) 0%, transparent 70%);
  animation-duration: 25s;
}
</style>
