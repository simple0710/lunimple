import type { Contest } from '../../../types/contest';
import { formatContestDate } from '../../../utils/dateFormat';
import styles from './ContestList.module.css';

interface Props {
  title: string;
  contests: Contest[];
}

export const ContestList = ({ title, contests }: Props) => {
  return (
    <div className={styles.wrapper}>
      <h3 className={styles.title}>{title}</h3>
      <ul className={styles.list}>
        {contests.length === 0 && (
          <li className={styles.empty}>등록된 대회가 없습니다.</li>
        )}
        {contests.map((c) => {
          const { date, time } = formatContestDate(c.startTime);
          return (
            <li key={`${c.name}-${c.startTime}`} className={styles.item}>
              <div className={styles.dateCol}>
                <span>{date}</span>
                <span>{time}</span>
              </div>
              <div className={styles.infoCol}>
                <span className={styles.type}>{c.contestType}</span>
                <span className={styles.name}>{c.name}</span>
              </div>
            </li>
          );
        })}
      </ul>
    </div>
  );
};
