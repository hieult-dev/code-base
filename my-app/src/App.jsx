import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

// 🧩 Function component con – nhận props từ App
function UserInfo({ user, onIncreaseAge, onCityChange }) {
  return (
    <div className="card">
      <h2>👤 Thông tin người dùng (component con)</h2>
      <p>Tên: {user.name}</p>
      <p>Tuổi: {user.age}</p>
      <p>Thành phố: {user.city}</p>

      <button onClick={onIncreaseAge}>Tăng tuổi</button>
      <br /><br />
      <input
        type="text"
        value={user.city}
        onChange={onCityChange}
        placeholder="Nhập thành phố mới..."
      />

      <p>
        <code>user</code> hiện tại: {JSON.stringify(user)}
      </p>
    </div>
  )
}

function App() {
  const [count, setCount] = useState(0)

  const [user, setUser] = useState({
    name: 'Tu',
    age: 21,
    city: 'Hà Nội'
  })

  const increaseAge = () => {
    setUser({ ...user, age: user.age + 1 })
  }

  const handleCityChange = (e) => {
    setUser({ ...user, city: e.target.value })
  }

  // chạy mỗi khi render lại
  useEffect(() => {
    console.log('Hello, chạy mỗi khi màn hình render lại')
  })
  // chạy duy nhất 1 lần khi render lần đầu
  useEffect(() => {
    console.log('Chạy 1 lần duy nhất khi mở trang')
  }, [])
  // chạy khi các dep thay đổi
  useEffect(() => {
    console.log('Count: ', count)
  }, [count])

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
      </div>

      {/* 🔽 Dùng component con, truyền props xuống */}
      <UserInfo
        user={user}                  // object user → props.user
        onIncreaseAge={increaseAge}  // function → props.onIncreaseAge
        onCityChange={handleCityChange} // function → props.onCityChange
      />

      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  )
}

export default App
