<template>
  <div class="consciousness-notifications" :class="{ active: isVisible }">
    <div class="notifications-header">
      <h3 class="notifications-title">
        <span class="title-icon">🔔</span>
        Sacred Notifications
      </h3>
      
      <div class="header-actions">
        <button 
          class="mark-all-read-btn"
          @click="markAllAsRead"
          v-if="unreadCount > 0"
        >
          Mark All Read
        </button>
        
        <button class="close-btn" @click="closeNotifications">
          <span>✕</span>
        </button>
      </div>
    </div>
    
    <div class="notifications-content">
      <!-- Unread Notifications -->
      <div v-if="unreadNotifications.length > 0" class="notification-section">
        <h4 class="section-title">
          <span class="unread-indicator">●</span>
          New ({{ unreadNotifications.length }})
        </h4>
        
        <div class="notification-list">
          <div 
            v-for="notification in unreadNotifications" 
            :key="notification.id"
            class="notification-item unread"
            @click="handleNotificationClick(notification)"
          >
            <div class="notification-icon">{{ getNotificationIcon(notification.type) }}</div>
            
            <div class="notification-content">
              <h5 class="notification-title">{{ notification.title }}</h5>
              <p class="notification-message">{{ notification.message }}</p>
              
              <div class="notification-meta">
                <span class="notification-time">{{ formatTime(notification.timestamp) }}</span>
                <span class="notification-category">{{ formatCategory(notification.category) }}</span>
              </div>
            </div>
            
            <div class="notification-actions">
              <button 
                class="action-btn primary"
                @click.stop="performAction(notification)"
                v-if="notification.actionRequired"
              >
                {{ notification.actionText || 'View' }}
              </button>
              
              <button 
                class="action-btn secondary"
                @click.stop="markAsRead(notification.id)"
              >
                <span>👁️</span>
              </button>
            </div>
          </div>
        </div>
      </div>
      
      <!-- Read Notifications -->
      <div v-if="readNotifications.length > 0" class="notification-section">
        <h4 class="section-title">Recent</h4>
        
        <div class="notification-list">
          <div 
            v-for="notification in readNotifications.slice(0, 5)" 
            :key="notification.id"
            class="notification-item read"
            @click="handleNotificationClick(notification)"
          >
            <div class="notification-icon">{{ getNotificationIcon(notification.type) }}</div>
            
            <div class="notification-content">
              <h5 class="notification-title">{{ notification.title }}</h5>
              <p class="notification-message">{{ notification.message }}</p>
              
              <div class="notification-meta">
                <span class="notification-time">{{ formatTime(notification.timestamp) }}</span>
                <span class="notification-category">{{ formatCategory(notification.category) }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- Empty State -->
      <div v-if="allNotifications.length === 0" class="empty-state">
        <div class="empty-icon">🌟</div>
        <h4>No Sacred Messages</h4>
        <p>Your consciousness notifications will appear here</p>
      </div>
    </div>
    
    <!-- Notification Settings -->
    <div class="notifications-footer">
      <button class="settings-btn" @click="openSettings">
        <span class="settings-icon">⚙️</span>
        Notification Settings
      </button>
    </div>
  </div>
  
  <!-- Backdrop -->
  <div 
    v-if="isVisible" 
    class="notifications-backdrop"
    @click="closeNotifications"
  ></div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useConsciousnessStore } from '@/stores/consciousness'

// Props
interface Props {
  isVisible?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  isVisible: false
})

// Emits
const emit = defineEmits<{
  close: []
  settingsOpen: []
}>()

const router = useRouter()
const consciousnessStore = useConsciousnessStore()

// Computed
const allNotifications = computed(() => 
  consciousnessStore.notifications || sampleNotifications.value
)

const unreadNotifications = computed(() => 
  allNotifications.value.filter(n => !n.read)
)

const readNotifications = computed(() => 
  allNotifications.value.filter(n => n.read)
)

const unreadCount = computed(() => unreadNotifications.value.length)

// Sample data - in real app would come from store/API
const sampleNotifications = ref([
  {
    id: '1',
    type: 'CONSCIOUSNESS_LEVEL_UP',
    category: 'PROGRESS',
    title: 'Consciousness Expansion!',
    message: 'You\'ve reached the "Expanding" consciousness level. New quests and abilities unlocked!',
    timestamp: new Date(Date.now() - 1000 * 60 * 30).toISOString(),
    read: false,
    actionRequired: true,
    actionText: 'Explore',
    actionUrl: '/consciousness/profile'
  },
  {
    id: '2',
    type: 'SYNCHRONICITY_DETECTED',
    category: 'SYNCHRONICITY',
    title: 'Sacred Synchronicity Detected',
    message: 'Your meditation time (11:11) aligns with a cosmic portal. Document this moment.',
    timestamp: new Date(Date.now() - 1000 * 60 * 60 * 2).toISOString(),
    read: false,
    actionRequired: true,
    actionText: 'Record',
    actionUrl: '/consciousness/profile#synchronicities'
  },
  {
    id: '3',
    type: 'QUEST_COMPLETION',
    category: 'ACHIEVEMENT',
    title: 'Quest Completed!',
    message: 'Sacred Meditation Practice quest completed. +150 XP, +25 Consciousness Growth earned.',
    timestamp: new Date(Date.now() - 1000 * 60 * 60 * 4).toISOString(),
    read: false,
    actionRequired: false
  },
  {
    id: '4',
    type: 'COMMUNITY_INVITATION',
    category: 'COMMUNITY',
    title: 'Community Invitation',
    message: 'Sarah invited you to join the "Gene Keys Mastery" study group.',
    timestamp: new Date(Date.now() - 1000 * 60 * 60 * 6).toISOString(),
    read: false,
    actionRequired: true,
    actionText: 'Join',
    actionUrl: '/community/groups'
  },
  {
    id: '5',
    type: 'SACRED_INSIGHT',
    category: 'WISDOM',
    title: 'Daily Sacred Insight',
    message: 'Today\'s insight: "Your unique frequency contributes to the cosmic symphony of consciousness."',
    timestamp: new Date(Date.now() - 1000 * 60 * 60 * 24).toISOString(),
    read: true,
    actionRequired: false
  }
])

// Methods
function closeNotifications() {
  emit('close')
}

function openSettings() {
  emit('settingsOpen')
}

function markAsRead(notificationId: string) {
  const notification = sampleNotifications.value.find(n => n.id === notificationId)
  if (notification) {
    notification.read = true
    console.log(`🔔 Marked notification ${notificationId} as read`)
  }
}

function markAllAsRead() {
  sampleNotifications.value.forEach(notification => {
    notification.read = true
  })
  console.log('🔔 Marked all notifications as read')
}

function handleNotificationClick(notification: any) {
  markAsRead(notification.id)
  
  if (notification.actionUrl) {
    router.push(notification.actionUrl)
    closeNotifications()
  }
}

function performAction(notification: any) {
  if (notification.actionUrl) {
    router.push(notification.actionUrl)
    closeNotifications()
  }
  
  console.log(`🎯 Performed action for notification: ${notification.title}`)
}

function getNotificationIcon(type: string): string {
  const iconMap: Record<string, string> = {
    CONSCIOUSNESS_LEVEL_UP: '⬆️',
    SYNCHRONICITY_DETECTED: '✨', 
    QUEST_COMPLETION: '🏆',
    QUEST_AVAILABLE: '🎯',
    COMMUNITY_INVITATION: '👥',
    SACRED_INSIGHT: '💡',
    GENE_KEY_ACTIVATION: '🔑',
    SACRED_GEOMETRY_UNLOCK: '🌀',
    REMINDER: '⏰',
    SYSTEM: '⚙️'
  }
  return iconMap[type] || '🔔'
}

function formatCategory(category: string): string {
  const categoryMap: Record<string, string> = {
    PROGRESS: 'Progress',
    SYNCHRONICITY: 'Synchronicity',
    ACHIEVEMENT: 'Achievement', 
    COMMUNITY: 'Community',
    WISDOM: 'Wisdom',
    REMINDER: 'Reminder',
    SYSTEM: 'System'
  }
  return categoryMap[category] || category
}

function formatTime(timestamp: string): string {
  const now = new Date()
  const time = new Date(timestamp)
  const diffMs = now.getTime() - time.getTime()
  const diffMinutes = Math.floor(diffMs / (1000 * 60))
  const diffHours = Math.floor(diffMs / (1000 * 60 * 60))
  const diffDays = Math.floor(diffMs / (1000 * 60 * 60 * 24))
  
  if (diffMinutes < 60) {
    return `${diffMinutes}m ago`
  } else if (diffHours < 24) {
    return `${diffHours}h ago`
  } else {
    return `${diffDays}d ago`
  }
}

onMounted(() => {
  console.log('🔔 Consciousness Notifications activated')
})

// Auto-mark as read when visible for a while
watch(() => props.isVisible, (isVisible) => {
  if (isVisible) {
    // Auto-mark notifications as read after 5 seconds of being visible
    setTimeout(() => {
      if (props.isVisible) {
        unreadNotifications.value.forEach(notification => {
          if (notification.type !== 'QUEST_AVAILABLE' && notification.type !== 'COMMUNITY_INVITATION') {
            markAsRead(notification.id)
          }
        })
      }
    }, 5000)
  }
})
</script>

<style scoped lang="scss">
.consciousness-notifications {
  position: fixed;
  top: 80px;
  right: -400px;
  width: 380px;
  height: calc(100vh - 80px);
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(20px);
  border-left: 1px solid rgba(255, 255, 255, 0.1);
  z-index: 1100;
  transition: right 0.3s ease;
  display: flex;
  flex-direction: column;
  
  &.active {
    right: 0;
  }
}

.notifications-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1.5rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  
  .notifications-title {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    color: var(--sacred-gold);
    font-size: 1.2rem;
    margin: 0;
    
    .title-icon {
      font-size: 1.1rem;
    }
  }
  
  .header-actions {
    display: flex;
    gap: 0.75rem;
    
    .mark-all-read-btn {
      background: transparent;
      color: var(--sacred-blue);
      border: 1px solid var(--sacred-blue);
      border-radius: 6px;
      padding: 0.4rem 0.8rem;
      font-size: 0.8rem;
      cursor: pointer;
      transition: all 0.3s ease;
      
      &:hover {
        background: var(--sacred-blue);
        color: var(--sacred-light);
      }
    }
    
    .close-btn {
      background: transparent;
      color: var(--sacred-light);
      border: 1px solid rgba(255, 255, 255, 0.3);
      border-radius: 6px;
      padding: 0.4rem 0.6rem;
      cursor: pointer;
      transition: all 0.3s ease;
      
      &:hover {
        background: rgba(255, 255, 255, 0.1);
      }
    }
  }
}

.notifications-content {
  flex: 1;
  overflow-y: auto;
  padding: 0;
}

.notification-section {
  .section-title {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    padding: 1rem 1.5rem 0.5rem;
    color: var(--sacred-light);
    font-size: 0.9rem;
    font-weight: 600;
    margin: 0;
    
    .unread-indicator {
      color: #ff4757;
      font-size: 0.6rem;
    }
  }
}

.notification-list {
  .notification-item {
    display: flex;
    gap: 1rem;
    padding: 1rem 1.5rem;
    border-bottom: 1px solid rgba(255, 255, 255, 0.05);
    cursor: pointer;
    transition: all 0.3s ease;
    
    &:hover {
      background: rgba(255, 255, 255, 0.05);
    }
    
    &.unread {
      background: rgba(255, 215, 0, 0.03);
      border-left: 3px solid var(--sacred-gold);
    }
    
    &.read {
      opacity: 0.7;
    }
    
    .notification-icon {
      font-size: 1.2rem;
      width: 24px;
      text-align: center;
      flex-shrink: 0;
      margin-top: 0.2rem;
    }
    
    .notification-content {
      flex: 1;
      min-width: 0;
      
      .notification-title {
        color: var(--sacred-light);
        font-size: 0.9rem;
        font-weight: 600;
        margin: 0 0 0.25rem 0;
        line-height: 1.3;
      }
      
      .notification-message {
        color: var(--sacred-light);
        opacity: 0.8;
        font-size: 0.8rem;
        line-height: 1.4;
        margin: 0 0 0.5rem 0;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        overflow: hidden;
      }
      
      .notification-meta {
        display: flex;
        gap: 0.75rem;
        font-size: 0.7rem;
        
        .notification-time {
          color: var(--sacred-light);
          opacity: 0.6;
        }
        
        .notification-category {
          color: var(--sacred-blue);
          background: rgba(102, 102, 255, 0.1);
          padding: 0.15rem 0.4rem;
          border-radius: 8px;
        }
      }
    }
    
    .notification-actions {
      display: flex;
      flex-direction: column;
      gap: 0.5rem;
      align-items: flex-end;
      
      .action-btn {
        padding: 0.3rem 0.6rem;
        border-radius: 6px;
        font-size: 0.7rem;
        cursor: pointer;
        transition: all 0.3s ease;
        border: 1px solid;
        
        &.primary {
          background: var(--sacred-gold);
          color: var(--sacred-dark);
          border-color: var(--sacred-gold);
          
          &:hover {
            transform: translateY(-1px);
            box-shadow: 0 3px 8px rgba(255, 215, 0, 0.3);
          }
        }
        
        &.secondary {
          background: transparent;
          color: var(--sacred-light);
          border-color: rgba(255, 255, 255, 0.3);
          
          &:hover {
            background: rgba(255, 255, 255, 0.1);
          }
        }
      }
    }
  }
}

.empty-state {
  text-align: center;
  padding: 3rem 1.5rem;
  color: var(--sacred-light);
  
  .empty-icon {
    font-size: 3rem;
    margin-bottom: 1rem;
    opacity: 0.5;
  }
  
  h4 {
    margin: 0 0 0.5rem 0;
    opacity: 0.8;
  }
  
  p {
    margin: 0;
    opacity: 0.6;
    font-size: 0.9rem;
  }
}

.notifications-footer {
  padding: 1rem 1.5rem;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  
  .settings-btn {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    background: transparent;
    color: var(--sacred-light);
    border: 1px solid rgba(255, 255, 255, 0.2);
    border-radius: 8px;
    padding: 0.75rem;
    width: 100%;
    cursor: pointer;
    transition: all 0.3s ease;
    
    &:hover {
      background: rgba(255, 255, 255, 0.05);
    }
    
    .settings-icon {
      font-size: 1rem;
    }
  }
}

.notifications-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.3);
  z-index: 1000;
}

// Custom scrollbar
.notifications-content {
  &::-webkit-scrollbar {
    width: 6px;
  }
  
  &::-webkit-scrollbar-track {
    background: rgba(255, 255, 255, 0.1);
  }
  
  &::-webkit-scrollbar-thumb {
    background: rgba(255, 255, 255, 0.3);
    border-radius: 3px;
    
    &:hover {
      background: rgba(255, 255, 255, 0.5);
    }
  }
}

// Mobile responsiveness
@media (max-width: 480px) {
  .consciousness-notifications {
    width: 100vw;
    right: -100vw;
    top: 0;
    height: 100vh;
    
    &.active {
      right: 0;
    }
  }
  
  .notifications-header {
    padding-top: 3rem; // Account for mobile status bar
  }
  
  .notification-item {
    padding: 1rem !important;
    
    .notification-content {
      .notification-title {
        font-size: 0.85rem !important;
      }
      
      .notification-message {
        font-size: 0.75rem !important;
      }
    }
  }
}

// Notification entrance animations
.notification-item {
  animation: slideInRight 0.3s ease-out;
}

@keyframes slideInRight {
  from {
    transform: translateX(100%);
    opacity: 0;
  }
  to {
    transform: translateX(0);
    opacity: 1;
  }
}

// Pulse animation for unread notifications
.notification-item.unread .notification-icon {
  animation: notificationPulse 2s ease-in-out infinite;
}

@keyframes notificationPulse {
  0%, 100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.1);
    filter: drop-shadow(0 0 8px currentColor);
  }
}
</style>
