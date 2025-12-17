import { UserContext } from '../context/UserContext.js'
import { useContext } from 'react'
export default function UserInfo() {
    const { user, increaseCount, handleCityChange } = useContext(UserContext);
    return (
        <div className="card">
            <h2>👤 Thông tin người dùng (component con)</h2>
            <p>Tên: {user.name}</p>
            <p>Tuổi: {user.age}</p>
            <p>Thành phố: {user.city}</p>

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