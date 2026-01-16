import React from 'react';
import { Camera } from 'lucide-react';
import { STATE_FORMATS, COUNTRIES, EYE_COLORS, LICENSE_CLASSES } from '../utils/constants';

const LicenseForm = ({ formData, setFormData, photo, onPhotoUpload, onSubmit, loading }) => {
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleLocationTypeChange = (e) => {
    const type = e.target.value;
    if (type === 'usa') {
      setFormData(prev => ({ ...prev, country: null, state: 'CA' }));
    } else {
      setFormData(prev => ({ ...prev, state: null, country: 'UK' }));
    }
  };

  return (
    <div className="bg-white dark:bg-gray-800 p-6 rounded-2xl shadow-xl">
      <h2 className="text-2xl font-bold mb-6 text-gray-900 dark:text-white">License Information</h2>
      
      <form onSubmit={onSubmit} className="space-y-4">
        {/* Location Type */}
        <div>
          <label className="block text-sm font-medium mb-2 text-gray-700 dark:text-gray-300">License Type</label>
          <select
            value={formData.state ? 'usa' : 'international'}
            onChange={handleLocationTypeChange}
            className="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
          >
            <option value="usa">USA (State)</option>
            <option value="international">International (Country)</option>
          </select>
        </div>

        {/* State or Country Selection */}
        {formData.state !== null && (
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-700 dark:text-gray-300">State *</label>
            <select
              name="state"
              value={formData.state || 'CA'}
              onChange={handleChange}
              required
              className="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
            >
              {Object.entries(STATE_FORMATS).map(([code, data]) => (
                <option key={code} value={code}>{data.name}</option>
              ))}
            </select>
            {formData.state && (
              <p className="text-xs mt-1 text-gray-500 dark:text-gray-400">
                Format: {STATE_FORMATS[formData.state].example}
              </p>
            )}
          </div>
        )}

        {formData.country !== null && (
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-700 dark:text-gray-300">Country *</label>
            <select
              name="country"
              value={formData.country || 'UK'}
              onChange={handleChange}
              required
              className="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
            >
              {Object.entries(COUNTRIES).map(([code, data]) => (
                <option key={code} value={code}>{data.name}</option>
              ))}
            </select>
          </div>
        )}

        {/* Name Fields */}
        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-700 dark:text-gray-300">First Name *</label>
            <input
              type="text"
              name="firstName"
              value={formData.firstName}
              onChange={handleChange}
              required
              className="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
            />
          </div>
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-700 dark:text-gray-300">Last Name *</label>
            <input
              type="text"
              name="lastName"
              value={formData.lastName}
              onChange={handleChange}
              required
              className="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
            />
          </div>
        </div>

        {/* Date of Birth and Gender */}
        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-700 dark:text-gray-300">Date of Birth *</label>
            <input
              type="date"
              name="dob"
              value={formData.dob}
              onChange={handleChange}
              required
              className="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
            />
          </div>
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-700 dark:text-gray-300">Gender *</label>
            <select
              name="gender"
              value={formData.gender}
              onChange={handleChange}
              required
              className="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
            >
              <option value="M">Male</option>
              <option value="F">Female</option>
            </select>
          </div>
        </div>

        {/* Address */}
        <div>
          <label className="block text-sm font-medium mb-2 text-gray-700 dark:text-gray-300">Address</label>
          <input
            type="text"
            name="address"
            value={formData.address}
            onChange={handleChange}
            placeholder="123 Main Street"
            className="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
          />
        </div>

        {/* City and ZIP */}
        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-700 dark:text-gray-300">City</label>
            <input
              type="text"
              name="city"
              value={formData.city}
              onChange={handleChange}
              placeholder="Los Angeles"
              className="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
            />
          </div>
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-700 dark:text-gray-300">ZIP Code</label>
            <input
              type="text"
              name="zipCode"
              value={formData.zipCode}
              onChange={handleChange}
              placeholder="90001"
              maxLength="10"
              className="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
            />
          </div>
        </div>

        {/* Physical Description */}
        <div className="grid grid-cols-3 gap-4">
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-700 dark:text-gray-300">Eye Color</label>
            <select
              name="eyeColor"
              value={formData.eyeColor}
              onChange={handleChange}
              className="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
            >
              {Object.entries(EYE_COLORS).map(([code, name]) => (
                <option key={code} value={code}>{name}</option>
              ))}
            </select>
          </div>
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-700 dark:text-gray-300">Height</label>
            <input
              type="text"
              name="height"
              value={formData.height}
              onChange={handleChange}
              placeholder="5'10&quot;"
              className="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
            />
          </div>
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-700 dark:text-gray-300">Weight (lbs)</label>
            <input
              type="text"
              name="weight"
              value={formData.weight}
              onChange={handleChange}
              placeholder="185"
              className="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
            />
          </div>
        </div>

        {/* License Class */}
        <div>
          <label className="block text-sm font-medium mb-2 text-gray-700 dark:text-gray-300">License Class</label>
          <select
            name="licenseClass"
            value={formData.licenseClass}
            onChange={handleChange}
            className="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 dark:bg-gray-700 dark:text-white focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
          >
            {LICENSE_CLASSES.map(cls => (
              <option key={cls.value} value={cls.value}>{cls.label}</option>
            ))}
          </select>
        </div>

        {/* Photo Upload */}
        <div>
          <label className="block text-sm font-medium mb-2 text-gray-700 dark:text-gray-300">Upload Photo</label>
          <button
            type="button"
            onClick={() => document.getElementById('photo-input').click()}
            className="w-full px-4 py-3 rounded-lg border-2 border-dashed border-gray-300 dark:border-gray-600 hover:bg-gray-50 dark:hover:bg-gray-700 flex items-center justify-center gap-2 transition-colors"
          >
            <Camera className="w-5 h-5" />
            {photo ? 'Change Photo' : 'Add Photo'}
          </button>
          <input
            id="photo-input"
            type="file"
            accept="image/*"
            onChange={onPhotoUpload}
            className="hidden"
          />
          {photo && (
            <div className="mt-3">
              <img src={photo} alt="Preview" className="w-32 h-32 object-cover rounded-lg border-2 border-indigo-500" />
            </div>
          )}
        </div>

        {/* Submit Button */}
        <button
          type="submit"
          disabled={loading}
          className="w-full bg-indigo-600 hover:bg-indigo-700 disabled:bg-gray-400 disabled:cursor-not-allowed text-white py-3 rounded-lg font-semibold transition-colors flex items-center justify-center gap-2"
        >
          {loading ? (
            <>
              <div className="w-5 h-5 border-2 border-white border-t-transparent rounded-full animate-spin"></div>
              Generating Card Design...
            </>
          ) : (
            'Generate Professional License Card'
          )}
        </button>
      </form>
    </div>
  );
};

export default LicenseForm;