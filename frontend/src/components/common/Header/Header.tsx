import { Link, NavLink } from 'react-router-dom';
import { HeaderSearch } from './HeaderSearch';
import styles from './Header.module.css';

export const Header = () => {
  return (
    <header className={styles.header}>
      <div className={styles.inner}>
        <Link to="/" className={styles.logoLink} aria-label="LUNIMPLE 메인으로">
          <img src="/logo.png" alt="LUNIMPLE" className={styles.logoImg} />
          <span className={styles.logoText}>LUNIMPLE</span>
        </Link>

        <nav className={styles.nav}>
          <NavLink
            to="/problem"
            className={({ isActive }) =>
              isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
            }
          >
            Problem
          </NavLink>
        </nav>

        <div className={styles.right}>
          <HeaderSearch />
        </div>
      </div>
    </header>
  );
};
