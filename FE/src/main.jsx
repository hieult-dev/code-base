import '@/common/assets/styles.scss';
import { createRoot } from 'react-dom/client'
import App from './apps/App.jsx'
import 'primeflex/primeflex.css'

createRoot(document.getElementById('root')).render(
  <App />
)
