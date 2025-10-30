export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';

export const providersEndpoint = {
  search: `${API_BASE_URL}/providers`,
  bySlug: (slug) => `${API_BASE_URL}/providers/${slug}`
};

export const inquiryEndpoint = `${API_BASE_URL}/inquiries`;
