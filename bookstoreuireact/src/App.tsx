import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { Home } from './components/Home'
import { RouterShell } from './routes/RouterShell'

function App() {
  //const [count, setCount] = useState(0)

  return (
    <>
      <RouterShell></RouterShell>
    </>
  )
}

export default App
