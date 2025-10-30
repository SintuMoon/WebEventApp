import { useEffect, useMemo, useRef, useState } from 'react';
import axios from 'axios';
import { providersEndpoint } from '../config/api.js';

const baseFilters = {
  category: '',
  city: '',
  region: '',
  priceTier: '',
  latitude: '',
  longitude: '',
  radiusKm: '',
  page: 0,
  size: 12
};

export function useProviderSearch(defaultFilters = {}) {
  const initialFilters = useMemo(() => ({ ...baseFilters, ...defaultFilters }), [defaultFilters]);
  const initialRef = useRef(initialFilters);
  const [filters, setFilters] = useState(initialFilters);
  const [result, setResult] = useState({
    content: [],
    totalElements: 0,
    totalPages: 0,
    number: 0
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const params = useMemo(() => {
    const searchParams = new URLSearchParams();
    Object.entries(filters).forEach(([key, value]) => {
      if (value !== '' && value !== null && value !== undefined) {
        searchParams.append(key, value);
      }
    });
    return searchParams;
  }, [filters]);

  useEffect(() => {
    setFilters(initialFilters);
    initialRef.current = initialFilters;
  }, [initialFilters]);

  useEffect(() => {
    let active = true;
    async function load() {
      setLoading(true);
      setError(null);
      try {
        const response = await axios.get(providersEndpoint.search, {
          params: Object.fromEntries(params.entries())
        });
        if (!active) return;
        setResult(response.data);
      } catch (err) {
        if (!active) return;
        setError(err);
      } finally {
        if (active) {
          setLoading(false);
        }
      }
    }
    load();
    return () => {
      active = false;
    };
  }, [params]);

  const updateFilter = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  const resetFilters = () => setFilters(initialRef.current);

  return { filters, updateFilter, resetFilters, result, loading, error };
}
