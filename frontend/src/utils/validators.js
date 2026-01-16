export const validateForm = (formData) => {
  const errors = {};

  if (!formData.firstName || formData.firstName.trim() === '') {
    errors.firstName = 'First name is required';
  }

  if (!formData.lastName || formData.lastName.trim() === '') {
    errors.lastName = 'Last name is required';
  }

  if (!formData.dob) {
    errors.dob = 'Date of birth is required';
  }

  if (!formData.gender) {
    errors.gender = 'Gender is required';
  }

  if (!formData.state && !formData.country) {
    errors.location = 'Please select either a state or country';
  }

  return {
    isValid: Object.keys(errors).length === 0,
    errors
  };
};

export const formatDate = (dateString) => {
  if (!dateString) return '';
  const date = new Date(dateString);
  return date.toLocaleDateString('en-US', { 
    year: 'numeric', 
    month: '2-digit', 
    day: '2-digit' 
  });
};