import { BrowserRouter } from 'react-router-dom'
import AppRouter from '../common/router/AppRouter'
import AppProvider from './AppProvider'
import ChatWidget from '@/apps/chatbot/components/ChatWidget.jsx'

export default function App() {
  return (
    <BrowserRouter>
      <AppProvider>
        <AppRouter />
        <ChatWidget userId={1} />
      </AppProvider>
    </BrowserRouter>
  )
}