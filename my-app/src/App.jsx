import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { UserContext } from './context/UserContext.js'
import { UserInfo } from './component/UserInfo.jsx'
function App() {
  const [count, setCount] = useState(5)
  const [count2, setCount2] = useState(0)

  const [user, setUser] = useState({
    name: 'Tu',
    age: 21,
    city: 'Hà Nội'
  })

  const increaseAge = () => {
    setUser({ ...user, age: user.age + 1 })
  }

  const changeCount2 = () => {
    setCount2((count2) => count2 + count)
  }

  const handleCityChange = (e) => {
    setUser({ ...user, city: e.target.value })
  }

  // // chạy mỗi khi render lại
  // useEffect(() => {
  //   console.log('Hello, chạy mỗi khi màn hình render lại')
  // })
  // // chạy duy nhất 1 lần khi render lần đầu
  // useEffect(() => {
  //   console.log('Chạy 1 lần duy nhất khi mở trang')
  // }, [])
  // // chạy khi các dep thay đổi
  // useEffect(() => {
  //   console.log('Count: ', count)
  // }, [count])

  const increaseCount = () => {
    setCount((count) => count + 1)
  }

  const decreaseCount = () => {
    setCount((count) => count - 1)
  }

  const resetCount = () => {
    setCount(0)
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
        <button onClick={changeCount2}>
          count2 is {count2} (tăng khi cộng dồn count vào)
        </button>
      </div>

      {/* 🔽 Dùng component con, truyền props xuống */}
      <UserContext.Provider
        value={{
          user,
          increaseAge,
          handleCityChange
        }}
      >
        <UserInfo />
      </UserContext.Provider>

      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  )
}

export default App
