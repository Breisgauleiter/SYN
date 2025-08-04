<template>
  <div class="contributing-test-page">
    <div class="test-container">
      <header class="test-header">
        <h1>🎯 Sacred Contributing System - Integration Test</h1>
        <p>Testing Backend ↔ Frontend Integration</p>
      </header>

      <!-- API Status Tests -->
      <section class="api-tests">
        <h2>📡 API Connection Tests</h2>
        
        <div class="test-group">
          <h3>Backend Health Check</h3>
          <button @click="testHealthCheck" :disabled="testing" class="test-button">
            {{ testing ? '🔄 Testing...' : '🔗 Test Backend Connection' }}
          </button>
          <div v-if="healthResult" class="test-result" :class="healthResult.success ? 'success' : 'error'">
            <pre>{{ JSON.stringify(healthResult, null, 2) }}</pre>
          </div>
        </div>

        <div class="test-group">
          <h3>Mock SCL Progress</h3>
          <button @click="testSCLProgress" :disabled="testing" class="test-button">
            {{ testing ? '🔄 Testing...' : '✨ Test SCL Progress API' }}
          </button>
          <div v-if="sclResult" class="test-result" :class="sclResult.success ? 'success' : 'error'">
            <pre>{{ JSON.stringify(sclResult, null, 2) }}</pre>
          </div>
        </div>

        <div class="test-group">
          <h3>Mock Quest Creation</h3>
          <button @click="testQuestCreation" :disabled="testing" class="test-button">
            {{ testing ? '🔄 Testing...' : '🎮 Test Quest Creation API' }}
          </button>
          <div v-if="questResult" class="test-result" :class="questResult.success ? 'success' : 'error'">
            <pre>{{ JSON.stringify(questResult, null, 2) }}</pre>
          </div>
        </div>
      </section>

      <!-- Store Integration Tests -->
      <section class="store-tests">
        <h2>🗄️ Store Integration Tests</h2>
        
        <div class="test-group">
          <h3>Contributing Store Initialization</h3>
          <button @click="testStoreInit" :disabled="testing" class="test-button">
            {{ testing ? '🔄 Testing...' : '🚀 Initialize Contributing Store' }}
          </button>
          <div v-if="storeResult" class="test-result" :class="storeResult.success ? 'success' : 'error'">
            <pre>{{ JSON.stringify(storeResult, null, 2) }}</pre>
          </div>
        </div>

        <div class="test-group">
          <h3>Store State Display</h3>
          <div class="store-state">
            <div class="state-item">
              <strong>Loading:</strong> {{ contributingStore.loading }}
            </div>
            <div class="state-item">
              <strong>Error:</strong> {{ contributingStore.error || 'None' }}
            </div>
            <div class="state-item">
              <strong>GitHub Connected:</strong> {{ contributingStore.isGitHubConnected }}
            </div>
            <div class="state-item">
              <strong>User Quests:</strong> {{ contributingStore.userQuests.length }}
            </div>
            <div class="state-item">
              <strong>Recommended Quests:</strong> {{ contributingStore.recommendedQuests.length }}
            </div>
          </div>
        </div>
      </section>

      <!-- Component Tests -->
      <section class="component-tests">
        <h2>🧩 Component Tests</h2>
        
        <div class="test-group">
          <h3>Quest Card Component</h3>
          <QuestCard 
            :quest="mockQuest"
            @start="handleQuestStart"
            @complete="handleQuestComplete"
            @cancel="handleQuestCancel"
          />
        </div>
      </section>

      <!-- Real-time Tests -->
      <section class="realtime-tests">
        <h2>⚡ Real-time Integration Tests</h2>
        
        <div class="test-group">
          <h3>Mock GitHub Synchronization</h3>
          <button @click="testGitHubSync" :disabled="testing" class="test-button">
            {{ testing ? '🔄 Syncing...' : '🔗 Test GitHub Sync' }}
          </button>
          <div v-if="syncResult" class="test-result" :class="syncResult.success ? 'success' : 'error'">
            <pre>{{ JSON.stringify(syncResult, null, 2) }}</pre>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useContributingStore } from '@/stores/contributing'
import { contributingAPI } from '@/services/contributingService'
import QuestCard from '@/components/contributing/QuestCard.vue'
import type { SacredQuest, GitHubIssue } from '@/types/contributing'

// Store
const contributingStore = useContributingStore()

// Test state
const testing = ref(false)
const healthResult = ref<any>(null)
const sclResult = ref<any>(null)
const questResult = ref<any>(null)
const storeResult = ref<any>(null)
const syncResult = ref<any>(null)

// Mock data for testing
const mockQuest: SacredQuest = {
  id: 'test-quest-1',
  title: 'Sacred Backend Integration Quest',
  description: 'Test the sacred connection between consciousness and technology through API integration. This quest validates the sacred flow of data between frontend and backend systems.',
  questType: 'PLATFORM_QUEST',
  businessTrack: 'TECH_DEVELOPER',
  status: 'PROPOSED',
  difficultyLevel: 5,
  experiencePoints: 250,
  estimatedHours: 4.0,
  userId: 'test-user-1',
  githubRepository: 'Breisgauleiter/SYN',
  githubIssueNumber: 42,
  githubUrl: 'https://github.com/Breisgauleiter/SYN/issues/42',
  createdAt: new Date().toISOString(),
  isLinkedToGitHub: true
}

const mockGitHubIssue: GitHubIssue = {
  id: 123,
  title: 'Implement Sacred Consciousness API',
  description: 'Create RESTful endpoints for sacred consciousness tracking and evolution.',
  repository: 'Breisgauleiter/SYN',
  issueNumber: 42,
  githubUrl: 'https://github.com/Breisgauleiter/SYN/issues/42',
  labels: ['sacred', 'api', 'consciousness'],
  assignees: ['sacred-developer'],
  state: 'open',
  difficulty: 'medium',
  estimatedHours: 4,
  createdAt: new Date().toISOString(),
  updatedAt: new Date().toISOString()
}

// Test methods
async function testHealthCheck() {
  testing.value = true
  try {
    const result = await contributingAPI.healthCheck()
    healthResult.value = { success: true, data: result }
  } catch (error) {
    healthResult.value = { 
      success: false, 
      error: error instanceof Error ? error.message : 'Unknown error' 
    }
  } finally {
    testing.value = false
  }
}

async function testSCLProgress() {
  testing.value = true
  try {
    const result = await contributingAPI.getSCLProgress('test-user-123')
    sclResult.value = { success: true, data: result }
  } catch (error) {
    sclResult.value = { 
      success: false, 
      error: error instanceof Error ? error.message : 'Unknown error' 
    }
  } finally {
    testing.value = false
  }
}

async function testQuestCreation() {
  testing.value = true
  try {
    const result = await contributingAPI.createQuestFromIssue(mockGitHubIssue)
    questResult.value = { success: true, data: result }
  } catch (error) {
    questResult.value = { 
      success: false, 
      error: error instanceof Error ? error.message : 'Unknown error' 
    }
  } finally {
    testing.value = false
  }
}

async function testStoreInit() {
  testing.value = true
  try {
    await contributingStore.initializeContributing('test-user-123')
    storeResult.value = { 
      success: true, 
      data: {
        loading: contributingStore.loading,
        error: contributingStore.error,
        questsCount: contributingStore.userQuests.length,
        sclLevel: contributingStore.currentSCLLevel
      }
    }
  } catch (error) {
    storeResult.value = { 
      success: false, 
      error: error instanceof Error ? error.message : 'Unknown error' 
    }
  } finally {
    testing.value = false
  }
}

async function testGitHubSync() {
  testing.value = true
  try {
    const result = await contributingStore.connectGitHub('mock-token', 'test-user-123')
    syncResult.value = { success: true, data: result }
  } catch (error) {
    syncResult.value = { 
      success: false, 
      error: error instanceof Error ? error.message : 'Unknown error' 
    }
  } finally {
    testing.value = false
  }
}

// Quest event handlers
function handleQuestStart(quest: SacredQuest) {
  console.log('Quest started:', quest.id)
  alert(`Starting quest: ${quest.title}`)
}

function handleQuestComplete(quest: SacredQuest) {
  console.log('Quest completed:', quest.id)
  alert(`Completed quest: ${quest.title}`)
}

function handleQuestCancel(quest: SacredQuest) {
  console.log('Quest cancelled:', quest.id)
  alert(`Cancelled quest: ${quest.title}`)
}
</script>

<style scoped lang="scss">
@import '@/assets/scss/sacred-consciousness.scss';

.contributing-test-page {
  min-height: 100vh;
  background: linear-gradient(135deg, var(--sacred-gradient-dark));
  color: var(--sacred-text-light);
  padding: 2rem;
}

.test-container {
  max-width: 1200px;
  margin: 0 auto;
}

.test-header {
  text-align: center;
  margin-bottom: 3rem;
  
  h1 {
    font-size: 2.5rem;
    color: var(--sacred-gold);
    margin-bottom: 1rem;
  }
  
  p {
    font-size: 1.2rem;
    opacity: 0.8;
  }
}

.api-tests,
.store-tests,
.component-tests,
.realtime-tests {
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(10px);
  border-radius: var(--sacred-border-radius);
  padding: 2rem;
  margin-bottom: 2rem;
  border: 1px solid rgba(255, 255, 255, 0.1);
  
  h2 {
    color: var(--sacred-gold);
    margin-bottom: 1.5rem;
    font-size: 1.5rem;
  }
}

.test-group {
  margin-bottom: 2rem;
  
  h3 {
    color: var(--sacred-text-light);
    margin-bottom: 1rem;
    font-size: 1.2rem;
  }
}

.test-button {
  background: var(--sacred-gradient-primary);
  color: white;
  border: none;
  padding: 1rem 2rem;
  border-radius: var(--sacred-border-radius);
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s ease;
  margin-bottom: 1rem;
  
  &:hover:not(:disabled) {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(129, 140, 248, 0.4);
  }
  
  &:disabled {
    opacity: 0.6;
    cursor: not-allowed;
    transform: none;
  }
}

.test-result {
  background: rgba(0, 0, 0, 0.3);
  border-radius: var(--sacred-border-radius);
  padding: 1rem;
  margin-top: 1rem;
  font-family: 'Monaco', 'Consolas', monospace;
  font-size: 0.9rem;
  overflow-x: auto;
  
  &.success {
    border-left: 4px solid var(--sacred-green);
  }
  
  &.error {
    border-left: 4px solid var(--sacred-red);
  }
  
  pre {
    margin: 0;
    white-space: pre-wrap;
    word-wrap: break-word;
  }
}

.store-state {
  background: rgba(0, 0, 0, 0.2);
  border-radius: var(--sacred-border-radius);
  padding: 1rem;
  
  .state-item {
    margin-bottom: 0.5rem;
    font-family: 'Monaco', 'Consolas', monospace;
    font-size: 0.9rem;
    
    strong {
      color: var(--sacred-gold);
    }
  }
}

@media (max-width: 768px) {
  .contributing-test-page {
    padding: 1rem;
  }
  
  .test-header h1 {
    font-size: 2rem;
  }
  
  .api-tests,
  .store-tests,
  .component-tests,
  .realtime-tests {
    padding: 1rem;
  }
}
</style>
