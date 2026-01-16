import { useState } from 'react';
import {
  Upload, X, User, Calendar, MapPin, Ruler, Eye,
  Shield, Heart, Award, CheckCircle, AlertCircle, Camera, Globe, Flag
} from 'lucide-react';
import {
  COUNTRIES, EU_LICENSE_CATEGORIES, JAPAN_LICENSE_COLORS,
  isEUCountry, getSubJurisdictions
} from '../utils/constants';

const EnhancedLicenseForm = ({ onGenerate, loading, darkMode, apiStatus }) => {
  const [activeTab, setActiveTab] = useState('personal');
  const [photoPreview, setPhotoPreview] = useState(null);
  const [errors, setErrors] = useState({});

  // International license toggle
  const [isInternational, setIsInternational] = useState(false);

  const [formData, setFormData] = useState({
    // Personal Information
    firstName: '',
    lastName: '',
    middleName: '',
    dob: '',
    gender: 'M',

    // Address
    address: '',
    addressLine2: '',
    city: '',
    state: 'TX',
    zipCode: '',

    // Physical Characteristics
    eyeColor: 'BRO',
    hairColor: 'BRO',
    height: '',
    weight: '',

    // License Details
    licenseClass: 'C',
    restrictions: '',
    endorsements: '',

    // Special Indicators
    organDonor: false,
    veteran: false,

    // Photo
    photoBase64: null,

    // International fields
    country: null,
    subJurisdiction: null,
    licenseCategories: 'B',
    colorCode: 'BLUE',
    nationalId: ''
  });

  // All 50 US States + DC + 5 Territories
  const usJurisdictions = [
    { code: 'AL', name: 'Alabama' }, { code: 'AK', name: 'Alaska' },
    { code: 'AZ', name: 'Arizona' }, { code: 'AR', name: 'Arkansas' },
    { code: 'CA', name: 'California' }, { code: 'CO', name: 'Colorado' },
    { code: 'CT', name: 'Connecticut' }, { code: 'DE', name: 'Delaware' },
    { code: 'DC', name: 'District of Columbia' }, { code: 'FL', name: 'Florida' },
    { code: 'GA', name: 'Georgia' }, { code: 'HI', name: 'Hawaii' },
    { code: 'ID', name: 'Idaho' }, { code: 'IL', name: 'Illinois' },
    { code: 'IN', name: 'Indiana' }, { code: 'IA', name: 'Iowa' },
    { code: 'KS', name: 'Kansas' }, { code: 'KY', name: 'Kentucky' },
    { code: 'LA', name: 'Louisiana' }, { code: 'ME', name: 'Maine' },
    { code: 'MD', name: 'Maryland' }, { code: 'MA', name: 'Massachusetts' },
    { code: 'MI', name: 'Michigan' }, { code: 'MN', name: 'Minnesota' },
    { code: 'MS', name: 'Mississippi' }, { code: 'MO', name: 'Missouri' },
    { code: 'MT', name: 'Montana' }, { code: 'NE', name: 'Nebraska' },
    { code: 'NV', name: 'Nevada' }, { code: 'NH', name: 'New Hampshire' },
    { code: 'NJ', name: 'New Jersey' }, { code: 'NM', name: 'New Mexico' },
    { code: 'NY', name: 'New York' }, { code: 'NC', name: 'North Carolina' },
    { code: 'ND', name: 'North Dakota' }, { code: 'OH', name: 'Ohio' },
    { code: 'OK', name: 'Oklahoma' }, { code: 'OR', name: 'Oregon' },
    { code: 'PA', name: 'Pennsylvania' }, { code: 'RI', name: 'Rhode Island' },
    { code: 'SC', name: 'South Carolina' }, { code: 'SD', name: 'South Dakota' },
    { code: 'TN', name: 'Tennessee' }, { code: 'TX', name: 'Texas' },
    { code: 'UT', name: 'Utah' }, { code: 'VT', name: 'Vermont' },
    { code: 'VA', name: 'Virginia' }, { code: 'WA', name: 'Washington' },
    { code: 'WV', name: 'West Virginia' }, { code: 'WI', name: 'Wisconsin' },
    { code: 'WY', name: 'Wyoming' },
    { code: 'PR', name: 'Puerto Rico' }, { code: 'GU', name: 'Guam' },
    { code: 'VI', name: 'U.S. Virgin Islands' }, { code: 'AS', name: 'American Samoa' },
    { code: 'MP', name: 'Northern Mariana Islands' }
  ];

  const eyeColors = [
    { code: 'BLK', name: 'Black' }, { code: 'BLU', name: 'Blue' },
    { code: 'BRO', name: 'Brown' }, { code: 'GRY', name: 'Gray' },
    { code: 'GRN', name: 'Green' }, { code: 'HAZ', name: 'Hazel' },
    { code: 'MAR', name: 'Maroon' }, { code: 'PNK', name: 'Pink' }
  ];

  const hairColors = [
    { code: 'BAL', name: 'Bald' }, { code: 'BLK', name: 'Black' },
    { code: 'BLN', name: 'Blond' }, { code: 'BRO', name: 'Brown' },
    { code: 'GRY', name: 'Gray' }, { code: 'RED', name: 'Red/Auburn' },
    { code: 'SDY', name: 'Sandy' }, { code: 'WHI', name: 'White' }
  ];

  const licenseClasses = [
    { code: 'A', name: 'Class A - Commercial (26,001+ lbs)' },
    { code: 'B', name: 'Class B - Heavy Vehicles' },
    { code: 'C', name: 'Class C - Standard Passenger' },
    { code: 'D', name: 'Class D - Operator' },
    { code: 'M', name: 'Class M - Motorcycle' }
  ];

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handlePhotoUpload = (e) => {
    const file = e.target.files[0];
    if (file) {
      if (file.size > 5 * 1024 * 1024) {
        setErrors(prev => ({ ...prev, photo: 'Photo must be less than 5MB' }));
        return;
      }

      if (!file.type.startsWith('image/')) {
        setErrors(prev => ({ ...prev, photo: 'Please upload an image file' }));
        return;
      }

      const reader = new FileReader();
      reader.onloadend = () => {
        setPhotoPreview(reader.result);
        const base64 = reader.result.split(',')[1];
        setFormData(prev => ({ ...prev, photoBase64: base64 }));
        setErrors(prev => ({ ...prev, photo: null }));
      };
      reader.readAsDataURL(file);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // Basic validation
    const newErrors = {};
    if (!formData.firstName) newErrors.firstName = 'Required';
    if (!formData.lastName) newErrors.lastName = 'Required';
    if (!formData.dob) newErrors.dob = 'Required';
    if (!formData.address) newErrors.address = 'Required';
    if (!formData.city) newErrors.city = 'Required';

    // International-specific validation
    if (isInternational) {
      if (!formData.country) newErrors.country = 'Required';
      if (formData.country && COUNTRIES[formData.country]?.hasSubJurisdictions && !formData.subJurisdiction) {
        newErrors.subJurisdiction = 'Required';
      }
      if (formData.country === 'BRAZIL' && !formData.nationalId) {
        newErrors.nationalId = 'CPF is required for Brazil';
      }
    } else {
      // US license validation
      if (!formData.zipCode) newErrors.zipCode = 'Required';
    }

    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      return;
    }

    // Prepare submission data
    const submitData = {
      ...formData,
      isInternational,
      // Map the country key to the backend enum format if international
      countryFormat: isInternational ? formData.country : 'USA'
    };

    onGenerate(submitData);
  };

  const tabs = [
    { id: 'personal', label: 'Personal Info', icon: User },
    { id: 'address', label: 'Address', icon: MapPin },
    { id: 'physical', label: 'Physical', icon: Eye },
    { id: 'license', label: 'License', icon: Shield },
    { id: 'special', label: 'Special', icon: Award }
  ];

  return (
    <div className={`p-6 rounded-2xl ${
      darkMode ? 'bg-gray-800/80' : 'bg-white/80'
    } shadow-2xl backdrop-blur-lg border ${
      darkMode ? 'border-gray-700' : 'border-gray-200'
    }`}>
      {/* Header */}
      <div className="mb-6">
        <h3 className="text-2xl font-bold text-gray-800 dark:text-gray-200 flex items-center gap-2 mb-2">
          <Globe className="w-6 h-6 text-indigo-600 dark:text-indigo-400" />
          {isInternational
            ? `International License Form${formData.country ? ` - ${COUNTRIES[formData.country]?.name}` : ''}`
            : 'AAMVA License Form'}
        </h3>
        <p className="text-sm text-gray-500 dark:text-gray-400">
          {isInternational
            ? `Generate a ${formData.country && COUNTRIES[formData.country]?.documentName ? COUNTRIES[formData.country].documentName : 'driver\'s license'} with authentic formatting`
            : 'Fill in all required fields to generate a DL/ID-2020 compliant card'}
        </p>
      </div>

      {/* Tabs */}
      <div className="flex overflow-x-auto gap-2 mb-6 pb-2">
        {tabs.map((tab) => (
          <button
            key={tab.id}
            onClick={() => setActiveTab(tab.id)}
            className={`px-4 py-2.5 rounded-xl font-semibold whitespace-nowrap transition-all flex items-center gap-2 ${
              activeTab === tab.id
                ? darkMode
                  ? 'bg-indigo-600 text-white shadow-lg'
                  : 'bg-gradient-to-r from-indigo-600 to-purple-600 text-white shadow-lg'
                : darkMode
                ? 'bg-gray-700 text-gray-300 hover:bg-gray-600'
                : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
            }`}
          >
            <tab.icon className="w-4 h-4" />
            {tab.label}
          </button>
        ))}
      </div>

      <form onSubmit={handleSubmit} className="space-y-6">
        {/* Personal Information Tab */}
        {activeTab === 'personal' && (
          <div className="space-y-4 animate-fade-in">
            <div className="grid md:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                  First Name <span className="text-red-500">*</span>
                </label>
                <input
                  type="text"
                  name="firstName"
                  value={formData.firstName}
                  onChange={handleInputChange}
                  className={`w-full px-4 py-3 rounded-xl ${
                    darkMode
                      ? 'bg-gray-700 text-white border-gray-600'
                      : 'bg-white text-gray-900 border-gray-300'
                  } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all ${
                    errors.firstName ? 'border-red-500' : ''
                  }`}
                  placeholder="John"
                />
                {errors.firstName && (
                  <p className="mt-1 text-xs text-red-500 flex items-center gap-1">
                    <AlertCircle className="w-3 h-3" />
                    {errors.firstName}
                  </p>
                )}
              </div>

              <div>
                <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                  Last Name <span className="text-red-500">*</span>
                </label>
                <input
                  type="text"
                  name="lastName"
                  value={formData.lastName}
                  onChange={handleInputChange}
                  className={`w-full px-4 py-3 rounded-xl ${
                    darkMode
                      ? 'bg-gray-700 text-white border-gray-600'
                      : 'bg-white text-gray-900 border-gray-300'
                  } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all ${
                    errors.lastName ? 'border-red-500' : ''
                  }`}
                  placeholder="Smith"
                />
                {errors.lastName && (
                  <p className="mt-1 text-xs text-red-500 flex items-center gap-1">
                    <AlertCircle className="w-3 h-3" />
                    {errors.lastName}
                  </p>
                )}
              </div>
            </div>

            <div>
              <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                Middle Name
              </label>
              <input
                type="text"
                name="middleName"
                value={formData.middleName}
                onChange={handleInputChange}
                className={`w-full px-4 py-3 rounded-xl ${
                  darkMode
                    ? 'bg-gray-700 text-white border-gray-600'
                    : 'bg-white text-gray-900 border-gray-300'
                } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all`}
                placeholder="Michael (optional)"
              />
            </div>

            <div className="grid md:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2 flex items-center gap-2">
                  <Calendar className="w-4 h-4" />
                  Date of Birth <span className="text-red-500">*</span>
                </label>
                <input
                  type="date"
                  name="dob"
                  value={formData.dob}
                  onChange={handleInputChange}
                  className={`w-full px-4 py-3 rounded-xl ${
                    darkMode
                      ? 'bg-gray-700 text-white border-gray-600'
                      : 'bg-white text-gray-900 border-gray-300'
                  } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all ${
                    errors.dob ? 'border-red-500' : ''
                  }`}
                />
                {errors.dob && (
                  <p className="mt-1 text-xs text-red-500 flex items-center gap-1">
                    <AlertCircle className="w-3 h-3" />
                    {errors.dob}
                  </p>
                )}
              </div>

              <div>
                <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                  Gender <span className="text-red-500">*</span>
                </label>
                <select
                  name="gender"
                  value={formData.gender}
                  onChange={handleInputChange}
                  className={`w-full px-4 py-3 rounded-xl ${
                    darkMode
                      ? 'bg-gray-700 text-white border-gray-600'
                      : 'bg-white text-gray-900 border-gray-300'
                  } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all`}
                >
                  <option value="M">Male (M)</option>
                  <option value="F">Female (F)</option>
                  <option value="X">Non-Binary (X)</option>
                </select>
              </div>
            </div>

            {/* Photo Upload */}
            <div>
              <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2 flex items-center gap-2">
                <Camera className="w-4 h-4" />
                Photo
              </label>
              <div className="flex gap-4 items-start">
                {photoPreview && (
                  <div className="relative">
                    <img
                      src={photoPreview}
                      alt="Preview"
                      className="w-24 h-24 object-cover rounded-xl border-2 border-indigo-500"
                    />
                    <button
                      type="button"
                      onClick={() => {
                        setPhotoPreview(null);
                        setFormData(prev => ({ ...prev, photoBase64: null }));
                      }}
                      className="absolute -top-2 -right-2 p-1 bg-red-500 text-white rounded-full hover:bg-red-600 transition-all"
                    >
                      <X className="w-4 h-4" />
                    </button>
                  </div>
                )}
                <label className={`flex-1 px-6 py-4 border-2 border-dashed rounded-xl cursor-pointer transition-all ${
                  darkMode
                    ? 'border-gray-600 hover:border-indigo-500 bg-gray-700/50'
                    : 'border-gray-300 hover:border-indigo-500 bg-gray-50'
                } text-center`}>
                  <Upload className="w-8 h-8 mx-auto mb-2 text-indigo-600 dark:text-indigo-400" />
                  <span className="block text-sm font-semibold text-gray-700 dark:text-gray-300">
                    Click to upload photo
                  </span>
                  <span className="block text-xs text-gray-500 dark:text-gray-400 mt-1">
                    JPG, PNG (max 5MB)
                  </span>
                  <input
                    type="file"
                    accept="image/*"
                    onChange={handlePhotoUpload}
                    className="hidden"
                  />
                </label>
              </div>
              {errors.photo && (
                <p className="mt-1 text-xs text-red-500 flex items-center gap-1">
                  <AlertCircle className="w-3 h-3" />
                  {errors.photo}
                </p>
              )}
            </div>
          </div>
        )}

        {/* Address Tab */}
        {activeTab === 'address' && (
          <div className="space-y-4 animate-fade-in">
            {/* License Type Toggle */}
            <div className={`p-4 rounded-xl ${
              darkMode ? 'bg-gray-700/50' : 'bg-gray-50'
            }`}>
              <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-3 flex items-center gap-2">
                <Globe className="w-4 h-4" />
                License Type
              </label>
              <div className="flex gap-2">
                <button
                  type="button"
                  onClick={() => {
                    setIsInternational(false);
                    setFormData(prev => ({ ...prev, country: null, subJurisdiction: null }));
                  }}
                  className={`flex-1 px-4 py-3 rounded-xl font-semibold transition-all flex items-center justify-center gap-2 ${
                    !isInternational
                      ? 'bg-gradient-to-r from-indigo-600 to-purple-600 text-white shadow-lg'
                      : darkMode
                        ? 'bg-gray-600 text-gray-300 hover:bg-gray-500'
                        : 'bg-gray-200 text-gray-700 hover:bg-gray-300'
                  }`}
                >
                  <Flag className="w-4 h-4" />
                  US License
                </button>
                <button
                  type="button"
                  onClick={() => {
                    setIsInternational(true);
                    setFormData(prev => ({ ...prev, country: 'GERMANY' }));
                  }}
                  className={`flex-1 px-4 py-3 rounded-xl font-semibold transition-all flex items-center justify-center gap-2 ${
                    isInternational
                      ? 'bg-gradient-to-r from-indigo-600 to-purple-600 text-white shadow-lg'
                      : darkMode
                        ? 'bg-gray-600 text-gray-300 hover:bg-gray-500'
                        : 'bg-gray-200 text-gray-700 hover:bg-gray-300'
                  }`}
                >
                  <Globe className="w-4 h-4" />
                  International
                </button>
              </div>
            </div>

            {/* US State Selection */}
            {!isInternational && (
              <div>
                <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                  State/Jurisdiction <span className="text-red-500">*</span>
                </label>
                <div className="relative">
                  <select
                    name="state"
                    value={formData.state}
                    onChange={handleInputChange}
                    className={`w-full px-4 py-3 rounded-xl ${
                      darkMode
                        ? 'bg-gray-700 text-white border-gray-600'
                        : 'bg-white text-gray-900 border-gray-300'
                    } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all`}
                  >
                    {usJurisdictions.map((jurisdiction) => (
                      <option key={jurisdiction.code} value={jurisdiction.code}>
                        {jurisdiction.name} ({jurisdiction.code})
                      </option>
                    ))}
                  </select>
                </div>
              </div>
            )}

            {/* International Country Selection */}
            {isInternational && (
              <>
                <div>
                  <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                    Country <span className="text-red-500">*</span>
                  </label>
                  <select
                    name="country"
                    value={formData.country || ''}
                    onChange={(e) => {
                      const countryKey = e.target.value;
                      const country = COUNTRIES[countryKey];
                      setFormData(prev => ({
                        ...prev,
                        country: countryKey,
                        subJurisdiction: country?.hasSubJurisdictions ? getSubJurisdictions(countryKey)[0]?.code : null,
                        licenseCategories: isEUCountry(countryKey) ? 'B' : prev.licenseCategories,
                        colorCode: countryKey === 'JAPAN' ? 'BLUE' : prev.colorCode
                      }));
                    }}
                    className={`w-full px-4 py-3 rounded-xl ${
                      darkMode
                        ? 'bg-gray-700 text-white border-gray-600'
                        : 'bg-white text-gray-900 border-gray-300'
                    } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all`}
                  >
                    {Object.entries(COUNTRIES)
                      .filter(([key]) => key !== 'USA')
                      .map(([key, country]) => (
                        <option key={key} value={key}>
                          {country.name} {country.documentName ? `(${country.documentName})` : ''}
                        </option>
                      ))}
                  </select>
                </div>

                {/* Sub-jurisdiction for countries with states/provinces */}
                {formData.country && COUNTRIES[formData.country]?.hasSubJurisdictions && (
                  <div>
                    <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                      {formData.country === 'CANADA' ? 'Province/Territory' :
                       formData.country === 'AUSTRALIA' ? 'State/Territory' :
                       formData.country === 'INDIA' ? 'State' :
                       formData.country === 'BRAZIL' ? 'Estado' :
                       formData.country === 'MEXICO' ? 'Estado' : 'Region'} <span className="text-red-500">*</span>
                    </label>
                    <select
                      name="subJurisdiction"
                      value={formData.subJurisdiction || ''}
                      onChange={handleInputChange}
                      className={`w-full px-4 py-3 rounded-xl ${
                        darkMode
                          ? 'bg-gray-700 text-white border-gray-600'
                          : 'bg-white text-gray-900 border-gray-300'
                      } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all`}
                    >
                      {getSubJurisdictions(formData.country).map((subj) => (
                        <option key={subj.code} value={subj.code}>
                          {subj.name}
                        </option>
                      ))}
                    </select>
                  </div>
                )}

                {/* EU License Categories */}
                {isEUCountry(formData.country) && (
                  <div>
                    <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                      License Categories
                    </label>
                    <div className="grid grid-cols-5 gap-2">
                      {EU_LICENSE_CATEGORIES.map((cat) => (
                        <label
                          key={cat.code}
                          className={`flex items-center justify-center px-3 py-2 rounded-lg cursor-pointer transition-all text-sm font-medium ${
                            formData.licenseCategories?.split(',').includes(cat.code)
                              ? 'bg-indigo-600 text-white shadow-md'
                              : darkMode
                                ? 'bg-gray-700 text-gray-300 hover:bg-gray-600'
                                : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
                          }`}
                          title={`${cat.name}: ${cat.description}`}
                        >
                          <input
                            type="checkbox"
                            checked={formData.licenseCategories?.split(',').includes(cat.code)}
                            onChange={(e) => {
                              const currentCategories = formData.licenseCategories ? formData.licenseCategories.split(',').filter(c => c) : [];
                              if (e.target.checked) {
                                currentCategories.push(cat.code);
                              } else {
                                const idx = currentCategories.indexOf(cat.code);
                                if (idx > -1) currentCategories.splice(idx, 1);
                              }
                              setFormData(prev => ({
                                ...prev,
                                licenseCategories: currentCategories.join(',')
                              }));
                            }}
                            className="hidden"
                          />
                          {cat.code}
                        </label>
                      ))}
                    </div>
                    <p className="mt-2 text-xs text-gray-500 dark:text-gray-400">
                      Click to toggle categories. B (Cars) is most common.
                    </p>
                  </div>
                )}

                {/* Japan License Color */}
                {formData.country === 'JAPAN' && (
                  <div>
                    <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                      License Color (経験色)
                    </label>
                    <div className="grid grid-cols-3 gap-2">
                      {JAPAN_LICENSE_COLORS.map((color) => (
                        <label
                          key={color.code}
                          className={`flex flex-col items-center justify-center px-3 py-3 rounded-lg cursor-pointer transition-all text-center ${
                            formData.colorCode === color.code
                              ? color.code === 'GREEN'
                                ? 'bg-green-600 text-white shadow-md'
                                : color.code === 'BLUE'
                                  ? 'bg-blue-600 text-white shadow-md'
                                  : 'bg-yellow-500 text-gray-900 shadow-md'
                              : darkMode
                                ? 'bg-gray-700 text-gray-300 hover:bg-gray-600'
                                : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
                          }`}
                        >
                          <input
                            type="radio"
                            name="colorCode"
                            value={color.code}
                            checked={formData.colorCode === color.code}
                            onChange={handleInputChange}
                            className="hidden"
                          />
                          <span className="font-bold text-sm">{color.name}</span>
                          <span className="text-xs opacity-80">{color.description}</span>
                        </label>
                      ))}
                    </div>
                  </div>
                )}

                {/* Brazil CPF Number */}
                {formData.country === 'BRAZIL' && (
                  <div>
                    <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                      CPF (Cadastro de Pessoas Físicas)
                    </label>
                    <input
                      type="text"
                      name="nationalId"
                      value={formData.nationalId}
                      onChange={(e) => {
                        // Format CPF as XXX.XXX.XXX-XX
                        let value = e.target.value.replace(/\D/g, '');
                        if (value.length > 11) value = value.slice(0, 11);
                        if (value.length > 9) {
                          value = `${value.slice(0,3)}.${value.slice(3,6)}.${value.slice(6,9)}-${value.slice(9)}`;
                        } else if (value.length > 6) {
                          value = `${value.slice(0,3)}.${value.slice(3,6)}.${value.slice(6)}`;
                        } else if (value.length > 3) {
                          value = `${value.slice(0,3)}.${value.slice(3)}`;
                        }
                        setFormData(prev => ({ ...prev, nationalId: value }));
                      }}
                      placeholder="123.456.789-00"
                      maxLength="14"
                      className={`w-full px-4 py-3 rounded-xl ${
                        darkMode
                          ? 'bg-gray-700 text-white border-gray-600'
                          : 'bg-white text-gray-900 border-gray-300'
                      } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all`}
                    />
                    <p className="mt-1 text-xs text-gray-500 dark:text-gray-400">
                      Brazilian individual taxpayer registry number
                    </p>
                  </div>
                )}

                {/* India Aadhaar Number */}
                {formData.country === 'INDIA' && (
                  <div>
                    <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                      Aadhaar Number (Optional)
                    </label>
                    <input
                      type="text"
                      name="nationalId"
                      value={formData.nationalId}
                      onChange={(e) => {
                        // Format Aadhaar as XXXX XXXX XXXX
                        let value = e.target.value.replace(/\D/g, '');
                        if (value.length > 12) value = value.slice(0, 12);
                        if (value.length > 8) {
                          value = `${value.slice(0,4)} ${value.slice(4,8)} ${value.slice(8)}`;
                        } else if (value.length > 4) {
                          value = `${value.slice(0,4)} ${value.slice(4)}`;
                        }
                        setFormData(prev => ({ ...prev, nationalId: value }));
                      }}
                      placeholder="1234 5678 9012"
                      maxLength="14"
                      className={`w-full px-4 py-3 rounded-xl ${
                        darkMode
                          ? 'bg-gray-700 text-white border-gray-600'
                          : 'bg-white text-gray-900 border-gray-300'
                      } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all`}
                    />
                    <p className="mt-1 text-xs text-gray-500 dark:text-gray-400">
                      12-digit unique identification number
                    </p>
                  </div>
                )}
              </>
            )}

            <div>
              <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                Street Address <span className="text-red-500">*</span>
              </label>
              <input
                type="text"
                name="address"
                value={formData.address}
                onChange={handleInputChange}
                className={`w-full px-4 py-3 rounded-xl ${
                  darkMode
                    ? 'bg-gray-700 text-white border-gray-600'
                    : 'bg-white text-gray-900 border-gray-300'
                } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all ${
                  errors.address ? 'border-red-500' : ''
                }`}
                placeholder="123 Main Street"
              />
              {errors.address && (
                <p className="mt-1 text-xs text-red-500 flex items-center gap-1">
                  <AlertCircle className="w-3 h-3" />
                  {errors.address}
                </p>
              )}
            </div>

            <div>
              <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                Address Line 2 (Apt, Suite, etc.)
              </label>
              <input
                type="text"
                name="addressLine2"
                value={formData.addressLine2}
                onChange={handleInputChange}
                className={`w-full px-4 py-3 rounded-xl ${
                  darkMode
                    ? 'bg-gray-700 text-white border-gray-600'
                    : 'bg-white text-gray-900 border-gray-300'
                } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all`}
                placeholder="Apt 4B (optional)"
              />
            </div>

            <div className="grid md:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                  City <span className="text-red-500">*</span>
                </label>
                <input
                  type="text"
                  name="city"
                  value={formData.city}
                  onChange={handleInputChange}
                  className={`w-full px-4 py-3 rounded-xl ${
                    darkMode
                      ? 'bg-gray-700 text-white border-gray-600'
                      : 'bg-white text-gray-900 border-gray-300'
                  } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all ${
                    errors.city ? 'border-red-500' : ''
                  }`}
                  placeholder="Austin"
                />
                {errors.city && (
                  <p className="mt-1 text-xs text-red-500 flex items-center gap-1">
                    <AlertCircle className="w-3 h-3" />
                    {errors.city}
                  </p>
                )}
              </div>

              <div>
                <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                  ZIP Code <span className="text-red-500">*</span>
                </label>
                <input
                  type="text"
                  name="zipCode"
                  value={formData.zipCode}
                  onChange={handleInputChange}
                  className={`w-full px-4 py-3 rounded-xl ${
                    darkMode
                      ? 'bg-gray-700 text-white border-gray-600'
                      : 'bg-white text-gray-900 border-gray-300'
                  } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all ${
                    errors.zipCode ? 'border-red-500' : ''
                  }`}
                  placeholder="78701"
                  maxLength="10"
                />
                {errors.zipCode && (
                  <p className="mt-1 text-xs text-red-500 flex items-center gap-1">
                    <AlertCircle className="w-3 h-3" />
                    {errors.zipCode}
                  </p>
                )}
              </div>
            </div>
          </div>
        )}

        {/* Physical Characteristics Tab */}
        {activeTab === 'physical' && (
          <div className="space-y-4 animate-fade-in">
            <div className="grid md:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2 flex items-center gap-2">
                  <Eye className="w-4 h-4" />
                  Eye Color
                </label>
                <select
                  name="eyeColor"
                  value={formData.eyeColor}
                  onChange={handleInputChange}
                  className={`w-full px-4 py-3 rounded-xl ${
                    darkMode
                      ? 'bg-gray-700 text-white border-gray-600'
                      : 'bg-white text-gray-900 border-gray-300'
                  } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all`}
                >
                  {eyeColors.map((color) => (
                    <option key={color.code} value={color.code}>
                      {color.name} ({color.code})
                    </option>
                  ))}
                </select>
              </div>

              <div>
                <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                  Hair Color
                </label>
                <select
                  name="hairColor"
                  value={formData.hairColor}
                  onChange={handleInputChange}
                  className={`w-full px-4 py-3 rounded-xl ${
                    darkMode
                      ? 'bg-gray-700 text-white border-gray-600'
                      : 'bg-white text-gray-900 border-gray-300'
                  } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all`}
                >
                  {hairColors.map((color) => (
                    <option key={color.code} value={color.code}>
                      {color.name} ({color.code})
                    </option>
                  ))}
                </select>
              </div>
            </div>

            <div className="grid md:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2 flex items-center gap-2">
                  <Ruler className="w-4 h-4" />
                  Height (inches)
                </label>
                <input
                  type="number"
                  name="height"
                  value={formData.height}
                  onChange={handleInputChange}
                  className={`w-full px-4 py-3 rounded-xl ${
                    darkMode
                      ? 'bg-gray-700 text-white border-gray-600'
                      : 'bg-white text-gray-900 border-gray-300'
                  } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all`}
                  placeholder="70 (5'10&quot;)"
                  min="48"
                  max="96"
                />
              </div>

              <div>
                <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                  Weight (lbs)
                </label>
                <input
                  type="number"
                  name="weight"
                  value={formData.weight}
                  onChange={handleInputChange}
                  className={`w-full px-4 py-3 rounded-xl ${
                    darkMode
                      ? 'bg-gray-700 text-white border-gray-600'
                      : 'bg-white text-gray-900 border-gray-300'
                  } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all`}
                  placeholder="180"
                  min="50"
                  max="500"
                />
              </div>
            </div>
          </div>
        )}

        {/* License Details Tab */}
        {activeTab === 'license' && (
          <div className="space-y-4 animate-fade-in">
            <div>
              <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2 flex items-center gap-2">
                <Shield className="w-4 h-4" />
                License Class
              </label>
              <select
                name="licenseClass"
                value={formData.licenseClass}
                onChange={handleInputChange}
                className={`w-full px-4 py-3 rounded-xl ${
                  darkMode
                    ? 'bg-gray-700 text-white border-gray-600'
                    : 'bg-white text-gray-900 border-gray-300'
                } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all`}
              >
                {licenseClasses.map((cls) => (
                  <option key={cls.code} value={cls.code}>
                    {cls.name}
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                Restrictions (e.g., 01, 02, B)
              </label>
              <input
                type="text"
                name="restrictions"
                value={formData.restrictions}
                onChange={handleInputChange}
                className={`w-full px-4 py-3 rounded-xl ${
                  darkMode
                    ? 'bg-gray-700 text-white border-gray-600'
                    : 'bg-white text-gray-900 border-gray-300'
                } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all`}
                placeholder="B (Corrective lenses)"
              />
              <p className="mt-1 text-xs text-gray-500 dark:text-gray-400">
                Common: B (Corrective lenses), C (Mechanical aid), F (Outside mirrors)
              </p>
            </div>

            <div>
              <label className="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2">
                Endorsements (e.g., H, N, P, S, T)
              </label>
              <input
                type="text"
                name="endorsements"
                value={formData.endorsements}
                onChange={handleInputChange}
                className={`w-full px-4 py-3 rounded-xl ${
                  darkMode
                    ? 'bg-gray-700 text-white border-gray-600'
                    : 'bg-white text-gray-900 border-gray-300'
                } border-2 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 dark:focus:ring-indigo-800 transition-all`}
                placeholder="H, N"
              />
              <p className="mt-1 text-xs text-gray-500 dark:text-gray-400">
                H (Hazmat), N (Tank vehicles), P (Passenger), S (School bus), T (Double/triple trailers)
              </p>
            </div>
          </div>
        )}

        {/* Special Indicators Tab */}
        {activeTab === 'special' && (
          <div className="space-y-4 animate-fade-in">
            <div className={`p-6 rounded-xl ${
              darkMode ? 'bg-gray-700/50' : 'bg-gray-50'
            }`}>
              <h4 className="font-bold text-gray-800 dark:text-gray-200 mb-4 flex items-center gap-2">
                <Award className="w-5 h-5 text-indigo-600 dark:text-indigo-400" />
                Special Indicators
              </h4>
              <div className="space-y-4">
                <label className="flex items-center gap-3 cursor-pointer group">
                  <input
                    type="checkbox"
                    name="organDonor"
                    checked={formData.organDonor}
                    onChange={handleInputChange}
                    className="w-5 h-5 rounded border-2 border-gray-300 dark:border-gray-600 text-indigo-600 focus:ring-2 focus:ring-indigo-500"
                  />
                  <div className="flex-1">
                    <div className="flex items-center gap-2">
                      <Heart className="w-4 h-4 text-red-500" />
                      <span className="font-semibold text-gray-800 dark:text-gray-200">
                        Organ Donor
                      </span>
                    </div>
                    <p className="text-xs text-gray-500 dark:text-gray-400 mt-1">
                      Red badge will appear on card if selected
                    </p>
                  </div>
                </label>

                <label className="flex items-center gap-3 cursor-pointer group">
                  <input
                    type="checkbox"
                    name="veteran"
                    checked={formData.veteran}
                    onChange={handleInputChange}
                    className="w-5 h-5 rounded border-2 border-gray-300 dark:border-gray-600 text-indigo-600 focus:ring-2 focus:ring-indigo-500"
                  />
                  <div className="flex-1">
                    <div className="flex items-center gap-2">
                      <Award className="w-4 h-4 text-blue-500" />
                      <span className="font-semibold text-gray-800 dark:text-gray-200">
                        Veteran
                      </span>
                    </div>
                    <p className="text-xs text-gray-500 dark:text-gray-400 mt-1">
                      Blue badge will appear on card if selected
                    </p>
                  </div>
                </label>
              </div>
            </div>

            <div className={`p-6 rounded-xl ${
              darkMode ? 'bg-blue-500/10 border border-blue-500/20' : 'bg-blue-50 border border-blue-200'
            }`}>
              <h4 className="font-bold text-blue-800 dark:text-blue-200 mb-2 flex items-center gap-2">
                <CheckCircle className="w-5 h-5" />
                Auto-Calculated Features
              </h4>
              <ul className="space-y-2 text-sm text-blue-700 dark:text-blue-300">
                <li className="flex items-start gap-2">
                  <span className="text-blue-500">•</span>
                  <span><strong>Under 21 Badge:</strong> Automatically displayed if DOB indicates age under 21</span>
                </li>
                <li className="flex items-start gap-2">
                  <span className="text-blue-500">•</span>
                  <span><strong>License Number:</strong> Generated using state-specific algorithm</span>
                </li>
                <li className="flex items-start gap-2">
                  <span className="text-blue-500">•</span>
                  <span><strong>Issue/Expiration Dates:</strong> Calculated based on current date and state rules</span>
                </li>
                <li className="flex items-start gap-2">
                  <span className="text-blue-500">•</span>
                  <span><strong>Document Discriminator:</strong> Unique identifier auto-generated</span>
                </li>
              </ul>
            </div>
          </div>
        )}

        {/* Submit Button */}
        <div className="pt-6 border-t border-gray-200 dark:border-gray-700">
          <button
            type="submit"
            disabled={loading || apiStatus !== 'connected'}
            className={`w-full py-4 rounded-xl font-bold text-white text-lg transition-all transform hover:scale-[1.02] disabled:opacity-50 disabled:cursor-not-allowed disabled:transform-none ${
              loading
                ? 'bg-gray-400 cursor-wait'
                : 'bg-gradient-to-r from-indigo-600 via-purple-600 to-pink-600 hover:from-indigo-700 hover:via-purple-700 hover:to-pink-700 shadow-lg hover:shadow-xl'
            }`}
          >
            {loading ? (
              <span className="flex items-center justify-center gap-2">
                <svg className="animate-spin h-5 w-5" viewBox="0 0 24 24">
                  <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" fill="none" />
                  <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
                </svg>
                {isInternational ? 'Generating International License...' : 'Generating AAMVA Card...'}
              </span>
            ) : apiStatus !== 'connected' ? (
              'Backend Server Offline'
            ) : (
              <span className="flex items-center justify-center gap-2">
                {isInternational ? <Globe className="w-5 h-5" /> : <Shield className="w-5 h-5" />}
                {isInternational
                  ? `Generate ${formData.country && COUNTRIES[formData.country]?.documentName ? COUNTRIES[formData.country].documentName : 'International License'}`
                  : 'Generate AAMVA DL/ID-2020 Card'}
              </span>
            )}
          </button>
        </div>
      </form>
    </div>
  );
};

export default EnhancedLicenseForm;
