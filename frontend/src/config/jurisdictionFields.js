/**
 * Jurisdiction-specific field configuration
 * Defines which fields are required/optional for each jurisdiction
 */

export const jurisdictionFields = {
  // Default fields for all jurisdictions
  default: {
    required: ['firstName', 'lastName', 'dob', 'gender', 'address', 'city', 'zipCode'],
    optional: ['middleName', 'addressLine2', 'height', 'weight', 'eyeColor', 'hairColor', 'licenseClass', 'photoBase64']
  },

  // US States with specific requirements
  CA: {
    required: ['firstName', 'lastName', 'dob', 'gender', 'address', 'city', 'zipCode', 'eyeColor', 'height'],
    optional: ['middleName', 'addressLine2', 'weight', 'hairColor', 'licenseClass', 'restrictions', 'endorsements', 'organDonor', 'veteran'],
    classes: ['A', 'B', 'C', 'M'],
    restrictions: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10'],
    endorsements: ['H', 'N', 'P', 'S', 'T', 'X']
  },

  NY: {
    required: ['firstName', 'lastName', 'dob', 'gender', 'address', 'city', 'zipCode', 'eyeColor'],
    optional: ['middleName', 'addressLine2', 'height', 'weight', 'hairColor', 'licenseClass', 'restrictions', 'endorsements', 'organDonor'],
    classes: ['A', 'B', 'C', 'D', 'E', 'M'],
    restrictions: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'K', 'L', 'M', 'N', 'O', 'P', 'V'],
    endorsements: ['H', 'N', 'P', 'S', 'T', 'X']
  },

  TX: {
    required: ['firstName', 'lastName', 'dob', 'gender', 'address', 'city', 'zipCode'],
    optional: ['middleName', 'addressLine2', 'height', 'weight', 'eyeColor', 'hairColor', 'licenseClass', 'restrictions', 'endorsements', 'veteran'],
    classes: ['A', 'B', 'C', 'M'],
    restrictions: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y'],
    endorsements: ['H', 'L', 'N', 'P', 'S', 'T', 'X']
  },

  FL: {
    required: ['firstName', 'lastName', 'dob', 'gender', 'address', 'city', 'zipCode'],
    optional: ['middleName', 'addressLine2', 'height', 'weight', 'eyeColor', 'hairColor', 'licenseClass', 'restrictions', 'endorsements', 'organDonor'],
    classes: ['A', 'B', 'C', 'D', 'E'],
    restrictions: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'K', 'L', 'M', 'N', 'O', 'P'],
    endorsements: ['H', 'N', 'P', 'S', 'T', 'X']
  },

  IL: {
    required: ['firstName', 'lastName', 'dob', 'gender', 'address', 'city', 'zipCode'],
    optional: ['middleName', 'addressLine2', 'height', 'weight', 'eyeColor', 'hairColor', 'licenseClass', 'restrictions', 'endorsements', 'organDonor'],
    classes: ['A', 'B', 'C', 'D', 'L', 'M'],
    restrictions: ['B', 'C', 'D', 'E', 'F', 'H', 'K', 'L', 'M', 'N', 'P', 'S', 'Y', 'Z'],
    endorsements: ['H', 'L', 'N', 'P', 'S', 'T', 'X']
  },

  PA: {
    required: ['firstName', 'lastName', 'dob', 'gender', 'address', 'city', 'zipCode'],
    optional: ['middleName', 'addressLine2', 'height', 'weight', 'eyeColor', 'hairColor', 'licenseClass', 'restrictions', 'endorsements', 'organDonor'],
    classes: ['A', 'B', 'C', 'M'],
    restrictions: ['1', '2', '3', '4', '5', '6', '7', '8', '9'],
    endorsements: ['H', 'N', 'P', 'S', 'T', 'X']
  },

  OH: {
    required: ['firstName', 'lastName', 'dob', 'gender', 'address', 'city', 'zipCode'],
    optional: ['middleName', 'addressLine2', 'height', 'weight', 'eyeColor', 'hairColor', 'licenseClass', 'restrictions', 'endorsements', 'organDonor'],
    classes: ['A', 'B', 'C', 'D', 'E', 'M'],
    restrictions: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'I', 'K', 'L', 'M', 'N', 'O', 'W', 'X', 'Y'],
    endorsements: ['H', 'N', 'P', 'S', 'T', 'X']
  },

  NV: {
    required: ['firstName', 'lastName', 'dob', 'gender', 'address', 'city', 'zipCode'],
    optional: ['middleName', 'addressLine2', 'height', 'weight', 'eyeColor', 'hairColor', 'licenseClass', 'restrictions', 'endorsements', 'organDonor', 'veteran'],
    classes: ['A', 'B', 'C', 'D', 'M'],
    restrictions: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'V', 'Y', 'Z'],
    endorsements: ['H', 'N', 'P', 'S', 'T', 'X']
  }
};

/**
 * Get field configuration for a jurisdiction
 * @param {string} jurisdictionCode - State/country code
 * @returns {object} Field configuration
 */
export const getJurisdictionFields = (jurisdictionCode) => {
  return jurisdictionFields[jurisdictionCode] || jurisdictionFields.default;
};

/**
 * Check if a field is required for a jurisdiction
 * @param {string} jurisdictionCode - State/country code
 * @param {string} fieldName - Field name
 * @returns {boolean} True if required
 */
export const isFieldRequired = (jurisdictionCode, fieldName) => {
  const config = getJurisdictionFields(jurisdictionCode);
  return config.required.includes(fieldName);
};

/**
 * Get available license classes for a jurisdiction
 * @param {string} jurisdictionCode - State/country code
 * @returns {array} Array of license class codes
 */
export const getAvailableClasses = (jurisdictionCode) => {
  const config = getJurisdictionFields(jurisdictionCode);
  return config.classes || ['A', 'B', 'C', 'D', 'M'];
};

/**
 * Get available restrictions for a jurisdiction
 * @param {string} jurisdictionCode - State/country code
 * @returns {array} Array of restriction codes
 */
export const getAvailableRestrictions = (jurisdictionCode) => {
  const config = getJurisdictionFields(jurisdictionCode);
  return config.restrictions || [];
};

/**
 * Get available endorsements for a jurisdiction
 * @param {string} jurisdictionCode - State/country code
 * @returns {array} Array of endorsement codes
 */
export const getAvailableEndorsements = (jurisdictionCode) => {
  const config = getJurisdictionFields(jurisdictionCode);
  return config.endorsements || [];
};
