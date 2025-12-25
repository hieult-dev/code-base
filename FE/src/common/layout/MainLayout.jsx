import Header from './Header'
import Sidebar from './Sidebar'
import { Outlet } from 'react-router-dom'
import './MainLayout.css'

export default function MainLayout() {
    return (
        <div className="layout">
            <Header />
            <main className="content">
                <Outlet />
            </main>
        </div>
    )
}
