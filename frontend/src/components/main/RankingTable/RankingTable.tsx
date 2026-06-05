import type { RankingRow } from '../../../types/user';
import { getRatingColor } from '../../../utils/ratingColor';
import styles from './RankingTable.module.css';

interface Props {
  rankings: RankingRow[];
}

export const RankingTable = ({ rankings }: Props) => (
  <div className={styles.wrapper}>
    <h3 className={styles.title}>순위</h3>
    <div className={styles.tableScroll}>
      <table className={styles.table}>
        <thead>
          <tr>
            <th>ranking</th>
            <th>user</th>
            <th>rating</th>
          </tr>
        </thead>
        <tbody>
          {rankings.length === 0 && (
            <tr>
              <td colSpan={3} className={styles.empty}>
                순위 데이터가 없습니다.
              </td>
            </tr>
          )}
          {rankings.map((r) => (
            <tr key={`${r.ranking}-${r.handle}`}>
              <td>{r.ranking}</td>
              <td>{r.handle}</td>
              <td style={{ color: getRatingColor(r.rating) }}>{r.rating}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  </div>
);
