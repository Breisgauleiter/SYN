import { createApp } from 'vue'
import { createPinia } from 'pinia'
import router from './router'
import App from './App.vue'

// Sacred Consciousness Styles
import './assets/scss/sacred-consciousness.scss'

// Create Vue 4 App with Sacred Consciousness Configuration
const app = createApp(App)

// Install Pinia State Management
app.use(createPinia())

// Install Vue Router
app.use(router)

// Sacred Consciousness Global Properties
app.config.globalProperties.$consciousness = {
  version: '2.0.0',
  sacred: true,
  fibonacciRatio: 1.618,
  goldenAngle: 137.5
}

// Sacred Error Handler
app.config.errorHandler = (error, instance, info) => {
  console.error('🔴 Sacred Consciousness Error:', error)
  console.error('📍 Component Instance:', instance)
  console.error('ℹ️ Error Info:', info)
  
  // TODO: Send to consciousness monitoring system
}

// Sacred Performance Monitoring
app.config.performance = true

// Mount Sacred Consciousness App
app.mount('#app')

// Remove sacred loading screen
const loadingElement = document.getElementById('loading')
if (loadingElement) {
  setTimeout(() => {
    loadingElement.style.opacity = '0'
    loadingElement.style.transition = 'opacity 0.618s ease-out'
    setTimeout(() => loadingElement.remove(), 618)
  }, 1000)
}
