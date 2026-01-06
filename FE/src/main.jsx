import { createRoot } from 'react-dom/client'
import App from './apps/App.jsx'

import '@/common/assets/sass/_custom_style.scss';
import '@/common/assets/styles.scss';
import 'primereact/resources/themes/lara-light-blue/theme.css'

createRoot(document.getElementById('root')).render(
  <App />
)
