import React from 'react';
import { Download, CreditCard, CheckCircle, AlertCircle } from 'lucide-react';
import { downloadImage, downloadBothCards } from '../services/downloadService';

const CardPreview = ({ cardData, validationStatus }) => {
  if (!cardData) {
    return (
      <div className="bg-white dark:bg-gray-800 p-6 rounded-2xl shadow-xl">
        <h2 className="text-2xl font-bold mb-6 text-gray-900 dark:text-white">Generated License Card</h2>
        <div className="h-96 flex items-center justify-center opacity-50">
          <div className="text-center">
            <CreditCard className="w-24 h-24 mx-auto mb-4 text-gray-400" />
            <p className="text-gray-500 dark:text-gray-400">Fill in the form and generate to see your professional license card</p>
          </div>
        </div>
      </div>
    );
  }

  const { licenseData, frontCardBase64, backCardBase64, cardFormat } = cardData;

  const handleDownloadFront = () => {
    downloadImage(frontCardBase64, `license_front_${licenseData.licenseNumber}.png`);
  };

  const handleDownloadBack = () => {
    downloadImage(backCardBase64, `license_back_${licenseData.licenseNumber}.png`);
  };

  const handleDownloadBoth = () => {
    downloadBothCards(frontCardBase64, backCardBase64, licenseData.licenseNumber);
  };

  return (
    <div className="bg-white dark:bg-gray-800 p-6 rounded-2xl shadow-xl">
      <div className="flex justify-between items-center mb-6">
        <div>
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white">Generated License Card</h2>
          <p className="text-sm text-gray-500 dark:text-gray-400 mt-1">Format: {cardFormat}</p>
        </div>
        <button
          onClick={handleDownloadBoth}
          className="flex items-center gap-2 bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-lg transition-colors"
        >
          <Download className="w-5 h-5" />
          Download Both
        </button>
      </div>

      {validationStatus && (
        <div className={`mb-4 p-4 rounded-lg flex items-center gap-2 ${validationStatus.success ? 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200' : 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-200'}`}>
          {validationStatus.success ? <CheckCircle className="w-5 h-5" /> : <AlertCircle className="w-5 h-5" />}
          <span>{validationStatus.message}</span>
        </div>
      )}

      {/* Front Card */}
      <div className="mb-6">
        <div className="flex justify-between items-center mb-3">
          <h3 className="text-lg font-semibold text-gray-900 dark:text-white">Front Side</h3>
          <button
            onClick={handleDownloadFront}
            className="flex items-center gap-2 bg-indigo-600 hover:bg-indigo-700 text-white px-3 py-1.5 rounded-lg text-sm transition-colors"
          >
            <Download className="w-4 h-4" />
            Download Front
          </button>
        </div>
        <div className="border-4 border-gray-200 dark:border-gray-700 rounded-xl overflow-hidden">
          <img 
            src={`data:image/png;base64,${frontCardBase64}`}
            alt="License Front"
            className="w-full h-auto"
          />
        </div>
      </div>

      {/* Back Card */}
      <div>
        <div className="flex justify-between items-center mb-3">
          <h3 className="text-lg font-semibold text-gray-900 dark:text-white">Back Side</h3>
          <button
            onClick={handleDownloadBack}
            className="flex items-center gap-2 bg-purple-600 hover:bg-purple-700 text-white px-3 py-1.5 rounded-lg text-sm transition-colors"
          >
            <Download className="w-4 h-4" />
            Download Back
          </button>
        </div>
        <div className="border-4 border-gray-200 dark:border-gray-700 rounded-xl overflow-hidden">
          <img 
            src={`data:image/png;base64,${backCardBase64}`}
            alt="License Back"
            className="w-full h-auto"
          />
        </div>
      </div>

      {/* License Info */}
      <div className="mt-6 p-4 bg-gray-50 dark:bg-gray-700 rounded-lg">
        <h4 className="font-semibold mb-2 text-gray-900 dark:text-white">License Information</h4>
        <div className="grid grid-cols-2 gap-2 text-sm">
          <div>
            <span className="text-gray-600 dark:text-gray-400">Number:</span>
            <span className="ml-2 font-mono font-bold text-gray-900 dark:text-white">{licenseData.licenseNumber}</span>
          </div>
          <div>
            <span className="text-gray-600 dark:text-gray-400">Valid:</span>
            <span className={`ml-2 font-semibold ${licenseData.isValid ? 'text-green-600' : 'text-yellow-600'}`}>
              {licenseData.isValid ? 'Yes' : 'Check Format'}
            </span>
          </div>
          <div>
            <span className="text-gray-600 dark:text-gray-400">Issue:</span>
            <span className="ml-2 text-gray-900 dark:text-white">{licenseData.issueDate}</span>
          </div>
          <div>
            <span className="text-gray-600 dark:text-gray-400">Expires:</span>
            <span className="ml-2 text-gray-900 dark:text-white">{licenseData.expirationDate}</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CardPreview;