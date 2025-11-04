import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  // 1️⃣ State đầu tiên (ví dụ cũ)
  const [count, setCount] = useState(0)

  // 2️⃣ Object state nâng cao
  const [user, setUser] = useState({
    name: 'Tu',
    age: 21,
    city: 'Hà Nội'
  })

  // 3️⃣ Hàm cập nhật tuổi
  const increaseAge = () => {
    setUser({ ...user, age: user.age + 1 })
  }

  // 4️⃣ Hàm đổi thành phố
  const handleCityChange = (e) => {
    setUser({ ...user, city: e.target.value })
  }

  return (
    <>
      {/* Phần logo mặc định */}
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>

      <h1>Vite + React</h1>

      <div className="card">
        {/* Cũ: đếm số */}
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>

        {/* Mới: ví dụ về object */}
        <h2>👤 Thông tin người dùng</h2>
        <p>Tên: {user.name}</p>
        <p>Tuổi: {user.age}</p>
        <p>Thành phố: {user.city}</p>

        <button onClick={increaseAge}>Tăng tuổi</button>
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

      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  )
}

export default App
