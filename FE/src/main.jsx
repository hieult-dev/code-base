import '@/common/assets/styles.scss';
import { createRoot } from 'react-dom/client'
import App from './apps/App.jsx'
import 'primereact/resources/themes/lara-light-blue/theme.css'
import '@/common/assets/sass/_custom_style.scss';

createRoot(document.getElementById('root')).render(
  <App />
)
