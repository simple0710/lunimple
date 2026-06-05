export interface FormattedContestDate {
  date: string;
  time: string;
}

export const formatContestDate = (isoString: string): FormattedContestDate => {
  const date = new Date(isoString);
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const dayNames = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
  const dayName = dayNames[date.getDay()];
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  return { date: `${month}/${day}(${dayName})`, time: `${hours}:${minutes}` };
};
