import '@/common/assets/Header.css'
import { UserRole } from '@/apps/user/model/User'
import { useNavigate } from 'react-router-dom'
import { useUserStore } from "../store/user";

export default function Header() {
    const navigate = useNavigate()
    const userRole = useUserStore((state) => state.userRole)
    const isAdmin = userRole === UserRole.ADMIN

    return (
        <header className="layout-header">
            <div className="layout-header__inner">
                <h3 className="layout-header__title">
                    My first app with React
                </h3>

                {isAdmin && (
                    <button
                        className="layout-header__admin-btn"
                        onClick={() => navigate('/admin/course')}
                    >
                        To Admin Page
                    </button>
                )}
            </div>
        </header>
    )
}