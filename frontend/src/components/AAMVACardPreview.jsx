import React, { useState } from 'react';
import { Download, Eye, EyeOff, Shield, CheckCircle, AlertCircle, Loader, CreditCard, BarChart3, User, Calendar, MapPin } from 'lucide-react';
import { downloadBothCards } from '../services/downloadService';

const AAMVACardPreview = ({ cardData, validationStatus, loading, darkMode }) => {
  const [showBack, setShowBack] = useState(false);
  const [downloading, setDownloading] = useState(false);

  const handleDownload = async () => {
    if (!cardData || !cardData.frontCardBase64 || !cardData.backCardBase64) {
      alert('Card data not available. Please generate the card first.');
      return;
    }

    setDownloading(true);
    try {
      const licenseNumber = cardData.licenseData?.licenseNumber || 'license';
      const success = downloadBothCards(cardData.frontCardBase64, cardData.backCardBase64, licenseNumber);

      if (success) {
        // Show success feedback
        setTimeout(() => {
          setDownloading(false);
        }, 1500);
      } else {
        setDownloading(false);
        alert('Failed to download card images. Please try again.');
      }
    } catch (error) {
      console.error('Download error:', error);
      alert('Failed to download card images. Error: ' + error.message);
      setDownloading(false);
    }
  };

  if (loading) {
    return (
      <div className={`p-8 rounded-2xl ${
        darkMode ? 'bg-gray-800/80' : 'bg-white/80'
      } shadow-2xl backdrop-blur-lg border ${
        darkMode ? 'border-gray-700' : 'border-gray-200'
      } min-h-[600px] flex flex-col items-center justify-center`}>
        <Loader className="w-16 h-16 text-indigo-600 dark:text-indigo-400 animate-spin mb-6" />
        <h3 className="text-2xl font-bold text-gray-700 dark:text-gray-300 mb-3">
          Generating AAMVA-Compliant Card
        </h3>
        <p className="text-gray-500 dark:text-gray-400 text-center max-w-md">
          Creating professional card with security features, PDF417 barcode, and state-specific design...
        </p>
        <div className="mt-6 flex gap-3">
          {['Photo Processing', 'Barcode Generation', 'Security Features'].map((step, idx) => (
            <div
              key={idx}
              className={`px-4 py-2 rounded-lg text-sm font-semibold ${
                darkMode ? 'bg-indigo-500/20 text-indigo-300' : 'bg-indigo-100 text-indigo-700'
              } animate-pulse`}
              style={{ animationDelay: `${idx * 200}ms` }}
            >
              {step}
            </div>
          ))}
        </div>
      </div>
    );
  }

  if (!cardData && !validationStatus) {
    return (
      <div className={`p-12 rounded-2xl ${
        darkMode ? 'bg-gray-800/80' : 'bg-white/80'
      } shadow-2xl backdrop-blur-lg border ${
        darkMode ? 'border-gray-700' : 'border-gray-200'
      } min-h-[600px] flex flex-col items-center justify-center text-center`}>
        <div className={`p-6 rounded-full ${
          darkMode ? 'bg-indigo-500/20' : 'bg-indigo-100'
        } mb-6`}>
          <CreditCard className={`w-16 h-16 ${
            darkMode ? 'text-indigo-400' : 'text-indigo-600'
          }`} />
        </div>
        <h3 className="text-2xl font-bold text-gray-700 dark:text-gray-300 mb-3">
          AAMVA Card Preview
        </h3>
        <p className="text-gray-500 dark:text-gray-400 max-w-md mb-6">
          Fill in the form and click "Generate AAMVA Card" to create a professional driver's license with full DL/ID-2020 compliance.
        </p>
        <div className="grid grid-cols-2 gap-4 w-full max-w-md">
          {[
            { icon: Shield, label: 'Security Features' },
            { icon: BarChart3, label: 'PDF417 Barcode' },
            { icon: User, label: '30+ AAMVA Fields' },
            { icon: CheckCircle, label: 'Ghost Image' }
          ].map((feature, idx) => (
            <div
              key={idx}
              className={`p-4 rounded-xl ${
                darkMode ? 'bg-gray-700/50' : 'bg-gray-50'
              } text-left`}
            >
              <feature.icon className={`w-6 h-6 mb-2 ${
                darkMode ? 'text-indigo-400' : 'text-indigo-600'
              }`} />
              <p className="text-sm font-semibold text-gray-700 dark:text-gray-300">
                {feature.label}
              </p>
            </div>
          ))}
        </div>
      </div>
    );
  }

  return (
    <div className={`p-6 rounded-2xl ${
      darkMode ? 'bg-gray-800/80' : 'bg-white/80'
    } shadow-2xl backdrop-blur-lg border ${
      darkMode ? 'border-gray-700' : 'border-gray-200'
    }`}>
      {/* Header */}
      <div className="flex justify-between items-center mb-6">
        <div>
          <h3 className="text-2xl font-bold text-gray-800 dark:text-gray-200 flex items-center gap-2">
            <CreditCard className="w-6 h-6 text-indigo-600 dark:text-indigo-400" />
            Card Preview
          </h3>
          <p className="text-sm text-gray-500 dark:text-gray-400 mt-1">
            {showBack ? 'Back Side - PDF417 Barcode' : 'Front Side - AAMVA Compliant'}
          </p>
        </div>
        <div className="flex gap-2">
          <button
            onClick={() => setShowBack(!showBack)}
            className={`px-4 py-2 rounded-xl font-semibold transition-all flex items-center gap-2 ${
              darkMode
                ? 'bg-gray-700 hover:bg-gray-600 text-white'
                : 'bg-gray-100 hover:bg-gray-200 text-gray-900'
            }`}
          >
            {showBack ? <Eye className="w-4 h-4" /> : <EyeOff className="w-4 h-4" />}
            {showBack ? 'Show Front' : 'Show Back'}
          </button>
          {cardData && (
            <button
              onClick={handleDownload}
              disabled={downloading}
              className="px-4 py-2 bg-gradient-to-r from-indigo-600 to-purple-600 hover:from-indigo-700 hover:to-purple-700 text-white rounded-xl font-semibold transition-all flex items-center gap-2 shadow-lg hover:shadow-xl disabled:opacity-50 disabled:cursor-not-allowed"
            >
              <Download className="w-4 h-4" />
              {downloading ? 'Downloading...' : 'Download'}
            </button>
          )}
        </div>
      </div>

      {/* Validation Status */}
      {validationStatus && (
        <div className={`mb-6 p-4 rounded-xl ${
          validationStatus.success
            ? darkMode
              ? 'bg-green-500/20 border border-green-500/30'
              : 'bg-green-50 border border-green-200'
            : darkMode
            ? 'bg-red-500/20 border border-red-500/30'
            : 'bg-red-50 border border-red-200'
        }`}>
          <div className="flex items-start gap-3">
            <div className={`p-1.5 rounded-lg ${
              validationStatus.success
                ? 'bg-green-500/20'
                : 'bg-red-500/20'
            }`}>
              {validationStatus.success ? (
                <CheckCircle className="w-5 h-5 text-green-600 dark:text-green-400" />
              ) : (
                <AlertCircle className="w-5 h-5 text-red-600 dark:text-red-400" />
              )}
            </div>
            <div className="flex-1">
              <h4 className={`font-bold text-sm mb-1 ${
                validationStatus.success
                  ? 'text-green-800 dark:text-green-200'
                  : 'text-red-800 dark:text-red-200'
              }`}>
                {validationStatus.message}
              </h4>
              <p className={`text-xs ${
                validationStatus.success
                  ? 'text-green-700 dark:text-green-300'
                  : 'text-red-700 dark:text-red-300'
              }`}>
                {validationStatus.details}
              </p>
              {validationStatus.licenseNumber && (
                <div className="mt-2 flex items-center gap-2">
                  <span className={`text-xs font-semibold ${
                    darkMode ? 'text-gray-400' : 'text-gray-600'
                  }`}>
                    License #:
                  </span>
                  <code className={`text-xs font-mono px-2 py-1 rounded ${
                    darkMode ? 'bg-gray-700 text-gray-200' : 'bg-gray-100 text-gray-800'
                  }`}>
                    {validationStatus.licenseNumber}
                  </code>
                </div>
              )}
            </div>
          </div>
        </div>
      )}

      {/* Card Display */}
      {cardData && (
        <div className="space-y-6">
          <div className={`relative rounded-xl overflow-hidden ${
            darkMode ? 'bg-gray-900' : 'bg-gray-100'
          } p-4`}>
            <img
              src={`data:image/png;base64,${showBack ? cardData.backCardBase64 : cardData.frontCardBase64}`}
              alt={showBack ? 'Back of Card' : 'Front of Card'}
              className="w-full h-auto rounded-lg shadow-2xl border-4 border-white/10"
            />
            <div className="absolute top-6 right-6 px-3 py-1.5 bg-black/60 backdrop-blur-md rounded-full text-xs font-bold text-white">
              {showBack ? 'BACK' : 'FRONT'}
            </div>
          </div>

          {/* Card Info Grid */}
          {cardData.licenseData && !showBack && (
            <div className="grid grid-cols-2 gap-4">
              <div className={`p-4 rounded-xl ${
                darkMode ? 'bg-gray-700/50' : 'bg-gray-50'
              }`}>
                <div className="flex items-center gap-2 mb-2">
                  <User className="w-4 h-4 text-indigo-600 dark:text-indigo-400" />
                  <span className="text-xs font-semibold text-gray-500 dark:text-gray-400">
                    Cardholder
                  </span>
                </div>
                <p className="text-sm font-bold text-gray-800 dark:text-gray-200">
                  {cardData.licenseData.firstName} {cardData.licenseData.lastName}
                </p>
              </div>

              <div className={`p-4 rounded-xl ${
                darkMode ? 'bg-gray-700/50' : 'bg-gray-50'
              }`}>
                <div className="flex items-center gap-2 mb-2">
                  <Calendar className="w-4 h-4 text-purple-600 dark:text-purple-400" />
                  <span className="text-xs font-semibold text-gray-500 dark:text-gray-400">
                    Expiration
                  </span>
                </div>
                <p className="text-sm font-bold text-gray-800 dark:text-gray-200">
                  {cardData.licenseData.expirationDate}
                </p>
              </div>

              <div className={`p-4 rounded-xl ${
                darkMode ? 'bg-gray-700/50' : 'bg-gray-50'
              }`}>
                <div className="flex items-center gap-2 mb-2">
                  <MapPin className="w-4 h-4 text-green-600 dark:text-green-400" />
                  <span className="text-xs font-semibold text-gray-500 dark:text-gray-400">
                    State
                  </span>
                </div>
                <p className="text-sm font-bold text-gray-800 dark:text-gray-200">
                  {cardData.licenseData.state || 'N/A'}
                </p>
              </div>

              <div className={`p-4 rounded-xl ${
                darkMode ? 'bg-gray-700/50' : 'bg-gray-50'
              }`}>
                <div className="flex items-center gap-2 mb-2">
                  <Shield className="w-4 h-4 text-blue-600 dark:text-blue-400" />
                  <span className="text-xs font-semibold text-gray-500 dark:text-gray-400">
                    Class
                  </span>
                </div>
                <p className="text-sm font-bold text-gray-800 dark:text-gray-200">
                  {cardData.licenseData.licenseClass || 'C'}
                </p>
              </div>
            </div>
          )}

          {/* AAMVA Features Info */}
          {!showBack && (
            <div className={`p-4 rounded-xl ${
              darkMode ? 'bg-indigo-500/10 border border-indigo-500/20' : 'bg-indigo-50 border border-indigo-200'
            }`}>
              <div className="flex items-center gap-2 mb-3">
                <Shield className="w-5 h-5 text-indigo-600 dark:text-indigo-400" />
                <h4 className="font-bold text-sm text-indigo-800 dark:text-indigo-200">
                  AAMVA DL/ID-2020 Features
                </h4>
              </div>
              <div className="grid grid-cols-2 gap-2 text-xs">
                {[
                  'Ghost Image Security',
                  'Guilloche Patterns',
                  'Microprint Protection',
                  'UV Reactive Elements',
                  'Holographic Overlay',
                  'Photo Border Security'
                ].map((feature, idx) => (
                  <div key={idx} className="flex items-center gap-1.5">
                    <div className="w-1.5 h-1.5 rounded-full bg-indigo-600 dark:bg-indigo-400"></div>
                    <span className="text-indigo-700 dark:text-indigo-300">{feature}</span>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default AAMVACardPreview;
