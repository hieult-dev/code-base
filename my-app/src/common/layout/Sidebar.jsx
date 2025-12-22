import { NavLink } from 'react-router-dom'
import LogoutButton from '../pages/Logout';
export default function Sidebar() {
    return (
        <aside
            style={{
                width: '100px',
                background: '#374151',
                color: '#fff',
                position: 'fixed',
                top: '60px',
                bottom: 0,
                left: 0,
                padding: '20px'
            }}
        >
            <nav style={{ display: 'flex', flexDirection: 'column', gap: 10 }}>

                <NavLink
                    to="/home"
                    end
                    style={({ isActive }) => ({
                        color: '#fff',
                        fontWeight: isActive ? 'bold' : 'normal',
                        background: isActive ? '#1f2937' : 'transparent',
                        padding: '6px',
                        borderRadius: '4px'
                    })}
                >
                    Home
                </NavLink>

                <NavLink
                    to="/user"
                    style={({ isActive }) => ({
                        color: '#fff',
                        fontWeight: isActive ? 'bold' : 'normal',
                        background: isActive ? '#1f2937' : 'transparent',
                        padding: '6px',
                        borderRadius: '4px'
                    })}
                >
                    User
                </NavLink>

                <NavLink
                    to="/about"
                    style={({ isActive }) => ({
                        color: '#fff',
                        fontWeight: isActive ? 'bold' : 'normal',
                        background: isActive ? '#1f2937' : 'transparent',
                        padding: '6px',
                        borderRadius: '4px'
                    })}
                >
                    About
                </NavLink>
                <LogoutButton />
            </nav>
        </aside>
    )
}
