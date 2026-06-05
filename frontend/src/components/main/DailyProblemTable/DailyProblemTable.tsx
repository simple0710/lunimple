import type { ContestProblemList } from '../../../types/problem';
import styles from './DailyProblemTable.module.css';

interface Props {
  contests: ContestProblemList[];
  isLoading?: boolean;
}

const codeLabel = (code: string) => (code === 'EX' ? 'H/Ex' : code);

export const DailyProblemTable = ({ contests, isLoading }: Props) => {
  if (isLoading) {
    return <p className={styles.loading}>로딩 중...</p>;
  }

  const first = contests[0];
  const problems = first?.problems ?? [];

  return (
    <table className={styles.table}>
      <tbody>
        {problems.length === 0 && (
          <tr>
            <td colSpan={3} className={styles.empty}>
              오늘의 문제가 없습니다.
            </td>
          </tr>
        )}
        {problems.map((p) => (
          <tr key={p.code}>
            <td>{codeLabel(p.code)}</td>
            <td>
              <a href={p.url} target="_blank" rel="noreferrer">
                {p.title}
              </a>
            </td>
            <td>{p.difficulty}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};
