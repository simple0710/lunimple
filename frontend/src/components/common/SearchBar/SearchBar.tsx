import { useState, type KeyboardEvent } from 'react';
import styles from './SearchBar.module.css';

interface Props {
  onSearch: (handle: string) => void;
  recentSearches?: string[];
}

export const SearchBar = ({ onSearch, recentSearches = [] }: Props) => {
  const [value, setValue] = useState('');

  const handleSearch = () => {
    if (value.trim()) onSearch(value.trim());
  };

  const handleKeyDown = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') handleSearch();
  };

  return (
    <div className={styles.wrapper}>
      <div className={styles.inputRow}>
        <input
          className={styles.input}
          type="text"
          placeholder="input handle"
          value={value}
          onChange={(e) => setValue(e.target.value)}
          onKeyDown={handleKeyDown}
        />
        <button type="button" className={styles.button} onClick={handleSearch}>
          🔍
        </button>
      </div>
      {recentSearches.length > 0 && (
        <p className={styles.recent}>
          (최근 검색 기록) {recentSearches.join(', ')}
        </p>
      )}
    </div>
  );
};
