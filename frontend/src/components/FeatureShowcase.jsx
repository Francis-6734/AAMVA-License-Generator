import React from 'react';
import { X, Shield, Globe, Zap, Lock, Eye, Fingerprint, BarChart3, CheckCircle } from 'lucide-react';

const FeatureShowcase = ({ onClose, darkMode }) => {
  const features = [
    {
      category: 'AAMVA DL/ID-2020 Compliance',
      icon: Shield,
      color: 'blue',
      items: [
        { name: 'License Number (DAQ)', description: 'State-specific format with validation' },
        { name: 'Full Name (DCS/DAC/DAD)', description: 'Last, First, Middle name fields' },
        { name: 'Address (DAG/DAI/DAJ/DAK)', description: 'Complete address with city, state, ZIP' },
        { name: 'Dates (DBB/DBD/DBA)', description: 'DOB, issue date, expiration date' },
        { name: 'Physical (DAY/DAU/DAW/DAZ)', description: 'Eye color, height, weight, hair color' },
        { name: 'License Class (DCA)', description: 'A, B, C, D, M with descriptions' },
        { name: 'Document Discriminator (DCF)', description: 'Unique identifier per license' },
        { name: 'Endorsements/Restrictions (DCJ/DCK)', description: 'Professional display' }
      ]
    },
    {
      category: 'Security Features',
      icon: Lock,
      color: 'red',
      items: [
        { name: 'Ghost Image', description: 'Transparent duplicate photo for verification' },
        { name: 'Guilloche Patterns', description: 'Complex line work prevents photocopying' },
        { name: 'Microprint', description: '4pt text too small to reproduce accurately' },
        { name: 'UV Reactive Elements', description: 'Elements visible under UV light' },
        { name: 'Holographic Overlay', description: 'State-colored security layer' },
        { name: 'Photo Border', description: 'State-colored 4px border on photo' },
        { name: 'Gradient Overlay', description: 'Subtle security gradient on photo' },
        { name: 'Magnetic Stripe', description: 'Visual representation on back card' }
      ]
    },
    {
      category: 'Special Indicators',
      icon: Eye,
      color: 'green',
      items: [
        { name: 'Organ Donor Badge', description: 'Red circular badge auto-displays if applicable' },
        { name: 'Veteran Badge', description: 'Blue badge for military veterans' },
        { name: 'Under 21 Detection', description: 'Red warning badge auto-calculated from DOB' },
        { name: 'Auto-Positioning', description: 'Badges stack vertically on right side' }
      ]
    },
    {
      category: 'Barcode Technology',
      icon: BarChart3,
      color: 'purple',
      items: [
        { name: 'PDF417 2D Barcode', description: 'AAMVA-compliant with 30+ encoded fields' },
        { name: '1D Barcode', description: 'Back card compatibility barcode' },
        { name: 'ZXing Engine', description: 'Industry-standard barcode generation' },
        { name: 'High Density', description: 'Optimized for scanner reading' }
      ]
    },
    {
      category: 'Jurisdiction Coverage',
      icon: Globe,
      color: 'indigo',
      items: [
        { name: 'All 50 US States', description: 'Complete state coverage with accurate formats' },
        { name: 'Washington D.C.', description: 'District of Columbia support' },
        { name: '5 US Territories', description: 'Puerto Rico, Guam, USVI, American Samoa, N. Mariana' },
        { name: 'United Kingdom', description: 'EU format with 12-star flag' },
        { name: 'Canada', description: 'Bilingual (English/French) format' },
        { name: 'State Templates', description: 'TX, FL, PA, IL, CA, NV, NY, UK, Canada' },
        { name: 'License Formats', description: 'State-specific number patterns' },
        { name: 'AAMVA IIN Codes', description: 'Official 6-digit state identifiers' }
      ]
    },
    {
      category: 'Professional Typography',
      icon: Fingerprint,
      color: 'yellow',
      items: [
        { name: 'OCR A Extended', description: 'Machine-readable license numbers (24pt)' },
        { name: 'Header Font', description: 'Arial Bold 48pt for state names' },
        { name: 'Data Font', description: 'Arial Bold 16pt for field values' },
        { name: 'Label Font', description: 'Arial Bold 10pt for field labels' },
        { name: 'Microprint Font', description: 'Arial Plain 4pt for security text' },
        { name: 'Date Formatting', description: 'MM/DD/YYYY display format' }
      ]
    },
    {
      category: 'Advanced Features',
      icon: Zap,
      color: 'orange',
      items: [
        { name: 'High Resolution', description: '1050x660px card dimensions' },
        { name: 'Front & Back', description: 'Complete two-sided card generation' },
        { name: 'Photo Processing', description: 'Auto-resize and border application' },
        { name: 'Class Descriptions', description: 'Full text for each license class' },
        { name: 'Compliance Text', description: 'Legal warnings on back card' },
        { name: 'State Watermarks', description: 'Large transparent state name' },
        { name: 'Gradient Backgrounds', description: 'Professional color schemes' },
        { name: 'Downloadable PNG', description: 'High-quality image export' }
      ]
    }
  ];

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/60 backdrop-blur-sm animate-fade-in">
      <div className={`w-full max-w-6xl max-h-[90vh] overflow-y-auto rounded-2xl ${
        darkMode ? 'bg-gray-800' : 'bg-white'
      } shadow-2xl`}>
        {/* Header */}
        <div className={`sticky top-0 z-10 flex justify-between items-center p-6 border-b ${
          darkMode ? 'bg-gray-800 border-gray-700' : 'bg-white border-gray-200'
        } backdrop-blur-lg`}>
          <div>
            <h2 className="text-3xl font-black bg-gradient-to-r from-indigo-600 via-purple-600 to-pink-600 bg-clip-text text-transparent">
              AAMVA Feature Showcase
            </h2>
            <p className="text-gray-600 dark:text-gray-400 mt-1">
              Production-grade DL/ID-2020 compliant card generation system
            </p>
          </div>
          <button
            onClick={onClose}
            className={`p-2 rounded-xl transition-all ${
              darkMode ? 'hover:bg-gray-700' : 'hover:bg-gray-100'
            }`}
            aria-label="Close"
          >
            <X className="w-6 h-6" />
          </button>
        </div>

        {/* Content */}
        <div className="p-6 space-y-8">
          {features.map((feature, idx) => (
            <div key={idx}>
              <div className="flex items-center gap-3 mb-4">
                <div className={`p-3 rounded-xl bg-${feature.color}-500/10`}>
                  <feature.icon className={`w-6 h-6 text-${feature.color}-600 dark:text-${feature.color}-400`} />
                </div>
                <h3 className={`text-xl font-bold text-${feature.color}-600 dark:text-${feature.color}-400`}>
                  {feature.category}
                </h3>
              </div>
              <div className="grid md:grid-cols-2 gap-4">
                {feature.items.map((item, itemIdx) => (
                  <div
                    key={itemIdx}
                    className={`p-4 rounded-xl ${
                      darkMode ? 'bg-gray-700/50' : 'bg-gray-50'
                    } hover:shadow-lg transition-shadow`}
                  >
                    <div className="flex items-start gap-3">
                      <CheckCircle className={`w-5 h-5 text-${feature.color}-600 dark:text-${feature.color}-400 mt-0.5 flex-shrink-0`} />
                      <div>
                        <h4 className="font-bold text-sm text-gray-800 dark:text-gray-200 mb-1">
                          {item.name}
                        </h4>
                        <p className="text-xs text-gray-600 dark:text-gray-400">
                          {item.description}
                        </p>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </div>

        {/* Footer */}
        <div className={`sticky bottom-0 p-6 border-t ${
          darkMode ? 'bg-gray-800 border-gray-700' : 'bg-white border-gray-200'
        } backdrop-blur-lg`}>
          <div className={`p-4 rounded-xl ${
            darkMode ? 'bg-indigo-500/10 border border-indigo-500/20' : 'bg-indigo-50 border border-indigo-200'
          }`}>
            <p className="text-sm text-center text-indigo-700 dark:text-indigo-300">
              <strong>Total:</strong> 50+ AAMVA fields • 8 security features • 56 jurisdictions • Professional typography
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FeatureShowcase;
