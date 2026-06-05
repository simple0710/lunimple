import { useState, type KeyboardEvent } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './HeaderSearch.module.css';

export const HeaderSearch = () => {
  const navigate = useNavigate();
  const [value, setValue] = useState('');

  const handleSearch = () => {
    const trimmed = value.trim();
    if (!trimmed) return;
    navigate(`/search?handle=${encodeURIComponent(trimmed)}`);
  };

  const handleKeyDown = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') handleSearch();
  };

  return (
    <div className={styles.wrapper}>
      <input
        className={styles.input}
        type="text"
        placeholder="handle 검색"
        value={value}
        onChange={(e) => setValue(e.target.value)}
        onKeyDown={handleKeyDown}
        aria-label="handle 검색"
      />
      <button
        type="button"
        className={styles.button}
        onClick={handleSearch}
        aria-label="검색"
      >
        🔍
      </button>
    </div>
  );
};
