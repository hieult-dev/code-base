import './index.css'
import '@/common/assets/variables.css'
import { createRoot } from 'react-dom/client'
import App from './apps/App.jsx'
import 'primeflex/primeflex.css'

createRoot(document.getElementById('root')).render(
  <App />
)
