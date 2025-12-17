import { UserContext } from '../context/UserContext.js'
import { useContext } from 'react'
import { useLocation } from 'react-router-dom';

export default function UserInfo() {
    const { user, increaseCount, handleCityChange } = useContext(UserContext);
    const location = useLocation();
    const locationName = () => {
        return location.pathname;
    }
    return (
        <div className="card">
            <h2>👤 Thông tin người dùng (component con)</h2>
            <p>Tên: {user.name}</p>
            <p>Tuổi: {user.age}</p>
            <p>Thành phố: {user.city}</p>
            <p>Current location: {locationName()}</p>
            <p>Current state: {JSON.stringify(location.state)}</p>
            <button onClick={increaseCount}>Tăng tuổi</button>
            <br /><br />
            <input
                type="text"
                value={user.city}
                onChange={handleCityChange}
                placeholder="Nhập thành phố mới..."
            />

            <p>
                <code>user</code> hiện tại: {JSON.stringify(user)}
            </p>
        </div>
    )
}