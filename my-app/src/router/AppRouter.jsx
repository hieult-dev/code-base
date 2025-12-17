import { Routes, Route } from 'react-router-dom'
import MainLayout from '../layout/MainLayout.jsx'
import Home from '../pages/Home'
import User from '../pages/UserInfo'
import About from '../pages/About'

export default function AppRouter() {
    return (
        <Routes>
            <Route element={<MainLayout />}>
                <Route index element={<Home />} />
                <Route path="user" element={<User />} />
                <Route path="about" element={<About />} />
            </Route>
        </Routes>
    )
}
