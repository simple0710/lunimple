import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Header } from './components/common/Header/Header';
import { Footer } from './components/common/Footer/Footer';
import { MainPage } from './pages/MainPage';
import { ProblemPage } from './pages/ProblemPage';
import { SearchPage } from './pages/SearchPage';

function App() {
  return (
    <BrowserRouter>
      <Header />
      <main>
        <div className="layoutContainer">
          <Routes>
            <Route path="/" element={<MainPage />} />
            <Route path="/problem" element={<ProblemPage />} />
            <Route path="/search" element={<SearchPage />} />
          </Routes>
        </div>
      </main>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
