const BASE_URL = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080/api/v1';

export function getBaseUrl(): string {
  return BASE_URL.replace(/\/$/, '');
}

export function buildUrl(
  path: string,
  params?: Record<string, string | number | undefined | null>,
): string {
  const url = new URL(`${getBaseUrl()}${path.startsWith('/') ? path : `/${path}`}`);
  if (params) {
    for (const [key, value] of Object.entries(params)) {
      if (value !== undefined && value !== null && value !== '') {
        url.searchParams.set(key, String(value));
      }
    }
  }
  return url.toString();
}

export async function fetchApi<T>(url: string): Promise<T> {
  const res = await fetch(url);
  if (!res.ok) {
    throw new Error(`API 요청 실패: ${res.status}`);
  }
  return res.json();
}
