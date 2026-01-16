import api from './api';

export const checkHealth = async () => {
  try {
    const response = await api.get('/license/health');
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const generateLicense = async (licenseData) => {
  try {
    const response = await api.post('/license/generate', licenseData);
    return response.data;
  } catch (error) {
    throw error.response?.data || error.message;
  }
};

export const generateCard = async (licenseData) => {
  try {
    const response = await api.post('/license/generate/card', licenseData);
    return response.data;
  } catch (error) {
    throw error.response?.data || error.message;
  }
};