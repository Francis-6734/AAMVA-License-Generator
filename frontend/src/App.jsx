import React, { useState, useEffect } from 'react';
import { Moon, Sun, CreditCard, AlertCircle, CheckCircle, Shield, Zap, Globe } from 'lucide-react';
import EnhancedLicenseForm from './components/EnhancedLicenseForm';
import AAMVACardPreview from './components/AAMVACardPreview';
import FeatureShowcase from './components/FeatureShowcase';
import { generateCard, checkHealth } from './services/licenseService';
import './App.css';

function App() {
  const [darkMode, setDarkMode] = useState(false);
  const [loading, setLoading] = useState(false);
  const [apiStatus, setApiStatus] = useState('checking');
  const [generatedCard, setGeneratedCard] = useState(null);
  const [validationStatus, setValidationStatus] = useState(null);
  const [showFeatures, setShowFeatures] = useState(false);

  // Check API health on mount
  useEffect(() => {
    const checkAPI = async () => {
      try {
        await checkHealth();
        setApiStatus('connected');
        console.log('✅ Backend API is running');
      } catch (error) {
        setApiStatus('disconnected');
        console.error('❌ Backend API is not running');
      }
    };
    checkAPI();
  }, []);

  // Toggle dark mode
  useEffect(() => {
    if (darkMode) {
      document.documentElement.classList.add('dark');
    } else {
      document.documentElement.classList.remove('dark');
    }
  }, [darkMode]);

  const handleGenerateCard = async (formData) => {
    if (apiStatus !== 'connected') {
      setValidationStatus({
        success: false,
        message: 'Backend API is not running. Please start the server first.',
        details: 'Run: cd backend && mvn spring-boot:run'
      });
      return;
    }

    setLoading(true);
    setValidationStatus(null);

    try {
      console.log('🚀 Generating AAMVA-compliant card with data:', formData);
      const response = await generateCard(formData);

      setGeneratedCard(response);
      setValidationStatus({
        success: true,
        message: formData.isInternational
          ? `✅ International License Generated`
          : `✅ AAMVA DL/ID-2020 Compliant License Generated`,
        details: `${response.cardFormat} template with security features`,
        licenseNumber: response.licenseData?.licenseNumber,
        state: formData.isInternational ? formData.countryFormat : formData.state,
        aamvaCompliant: !formData.isInternational,
        isInternational: formData.isInternational
      });

      console.log('✅ Card generated successfully:', response);
    } catch (error) {
      console.error('❌ Error generating card:', error);
      setValidationStatus({
        success: false,
        message: `Generation Error`,
        details: error.message || 'Failed to generate license card. Please check the console for details.'
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={`min-h-screen transition-all duration-500 ${
      darkMode
        ? 'dark bg-gradient-to-br from-gray-900 via-gray-800 to-gray-900 text-white'
        : 'bg-gradient-to-br from-blue-50 via-indigo-50 to-purple-50 text-gray-900'
    }`}>
      {/* Animated Background Effect */}
      <div className="fixed inset-0 overflow-hidden pointer-events-none">
        <div className={`absolute w-96 h-96 rounded-full blur-3xl opacity-20 ${
          darkMode ? 'bg-blue-500' : 'bg-indigo-400'
        } -top-48 -left-48 animate-pulse`}></div>
        <div className={`absolute w-96 h-96 rounded-full blur-3xl opacity-20 ${
          darkMode ? 'bg-purple-500' : 'bg-purple-400'
        } -bottom-48 -right-48 animate-pulse`} style={{ animationDelay: '1s' }}></div>
      </div>

      <div className="container mx-auto px-4 py-8 max-w-7xl relative z-10">
        {/* Header */}
        <header className="mb-12">
          <div className="flex justify-between items-start mb-6">
            <div className="flex items-center gap-4">
              <div className={`p-4 rounded-2xl ${
                darkMode ? 'bg-gradient-to-br from-blue-600 to-purple-600' : 'bg-gradient-to-br from-indigo-500 to-purple-500'
              } shadow-2xl transform hover:scale-105 transition-transform duration-300`}>
                <CreditCard className="w-10 h-10 text-white" />
              </div>
              <div>
                <h1 className="text-5xl font-black bg-gradient-to-r from-indigo-600 via-purple-600 to-pink-600 bg-clip-text text-transparent mb-2 tracking-tight">
                  AAMVA License Generator
                </h1>
                <p className="text-lg text-gray-600 dark:text-gray-300 font-medium">
                  Production-Grade DL/ID-2020 Compliant Card System
                </p>
                <div className="flex items-center gap-4 mt-3">
                  {/* API Status */}
                  <div className="flex items-center gap-2">
                    <div className={`w-2.5 h-2.5 rounded-full ${
                      apiStatus === 'connected'
                        ? 'bg-green-500 animate-pulse shadow-lg shadow-green-500/50'
                        : apiStatus === 'disconnected'
                        ? 'bg-red-500 animate-pulse shadow-lg shadow-red-500/50'
                        : 'bg-yellow-500 animate-pulse'
                    }`}></div>
                    <span className={`text-sm font-semibold ${
                      apiStatus === 'connected' ? 'text-green-600 dark:text-green-400' : 'text-red-600 dark:text-red-400'
                    }`}>
                      {apiStatus === 'connected' ? 'API Connected' : apiStatus === 'disconnected' ? 'API Offline' : 'Connecting...'}
                    </span>
                  </div>
                  {/* AAMVA Badge */}
                  <div className={`px-3 py-1 rounded-full text-xs font-bold ${
                    darkMode ? 'bg-blue-500/20 text-blue-300 border border-blue-500/30' : 'bg-blue-100 text-blue-700 border border-blue-200'
                  } flex items-center gap-1.5`}>
                    <Shield className="w-3.5 h-3.5" />
                    AAMVA DL/ID-2020
                  </div>
                </div>
              </div>
            </div>
            <div className="flex gap-3">
              <button
                onClick={() => setShowFeatures(!showFeatures)}
                className={`px-5 py-3 rounded-xl font-semibold transition-all ${
                  darkMode
                    ? 'bg-gray-800 hover:bg-gray-700 text-white'
                    : 'bg-white hover:bg-gray-50 text-gray-900'
                } shadow-lg hover:shadow-xl transform hover:scale-105`}
              >
                <Zap className="w-5 h-5 inline mr-2" />
                Features
              </button>
              <button
                onClick={() => setDarkMode(!darkMode)}
                className={`p-3 rounded-xl transition-all ${
                  darkMode ? 'bg-gray-800 hover:bg-gray-700' : 'bg-white hover:bg-gray-50'
                } shadow-lg hover:shadow-xl transform hover:scale-105`}
                aria-label="Toggle dark mode"
              >
                {darkMode ? <Sun className="w-6 h-6 text-yellow-400" /> : <Moon className="w-6 h-6 text-indigo-600" />}
              </button>
            </div>
          </div>

          {/* Feature Badges */}
          <div className="flex flex-wrap gap-3">
            {[
              { icon: Shield, text: '30+ AAMVA Fields', color: 'blue' },
              { icon: CheckCircle, text: 'Ghost Image Security', color: 'green' },
              { icon: Globe, text: '12 Countries Supported', color: 'purple' },
              { icon: Zap, text: 'Real-time Generation', color: 'orange' }
            ].map((badge, idx) => (
              <div
                key={idx}
                className={`px-4 py-2 rounded-lg text-sm font-semibold flex items-center gap-2 ${
                  darkMode
                    ? `bg-${badge.color}-500/10 text-${badge.color}-300 border border-${badge.color}-500/20`
                    : `bg-${badge.color}-50 text-${badge.color}-700 border border-${badge.color}-200`
                } backdrop-blur-sm transform hover:scale-105 transition-transform cursor-default`}
              >
                <badge.icon className="w-4 h-4" />
                {badge.text}
              </div>
            ))}
          </div>
        </header>

        {/* API Status Warning */}
        {apiStatus === 'disconnected' && (
          <div className="bg-red-50 dark:bg-red-900/20 border-l-4 border-red-500 p-5 mb-8 rounded-r-2xl shadow-lg backdrop-blur-sm">
            <div className="flex items-start gap-4">
              <div className="p-2 bg-red-100 dark:bg-red-800/30 rounded-lg">
                <AlertCircle className="w-6 h-6 text-red-500" />
              </div>
              <div className="flex-1">
                <h3 className="font-bold text-red-800 dark:text-red-200 text-lg mb-2">Backend Server Not Running</h3>
                <p className="text-sm text-red-700 dark:text-red-300 mb-3">
                  The AAMVA-compliant backend server needs to be running to generate licenses.
                </p>
                <div className="bg-red-100 dark:bg-red-900/40 p-4 rounded-xl">
                  <p className="text-xs font-semibold text-red-800 dark:text-red-200 mb-2">Start the backend:</p>
                  <code className="block text-sm text-red-900 dark:text-red-100 font-mono">
                    cd backend && mvn spring-boot:run
                  </code>
                </div>
              </div>
            </div>
          </div>
        )}

        {/* Feature Showcase Modal */}
        {showFeatures && <FeatureShowcase onClose={() => setShowFeatures(false)} darkMode={darkMode} />}

        {/* Main Content Grid */}
        <div className="grid lg:grid-cols-2 gap-8 mb-12">
          {/* Enhanced Form */}
          <EnhancedLicenseForm
            onGenerate={handleGenerateCard}
            loading={loading}
            darkMode={darkMode}
            apiStatus={apiStatus}
          />

          {/* AAMVA Card Preview */}
          <AAMVACardPreview
            cardData={generatedCard}
            validationStatus={validationStatus}
            loading={loading}
            darkMode={darkMode}
          />
        </div>

        {/* Footer Info */}
        <footer className={`p-8 rounded-2xl ${
          darkMode ? 'bg-gray-800/80' : 'bg-white/80'
        } shadow-2xl backdrop-blur-lg border ${
          darkMode ? 'border-gray-700' : 'border-gray-200'
        }`}>
          <div className="grid md:grid-cols-3 gap-8 mb-6">
            <div>
              <h3 className="text-xl font-bold mb-4 text-indigo-600 dark:text-indigo-400 flex items-center gap-2">
                <Shield className="w-5 h-5" />
                AAMVA Compliance
              </h3>
              <ul className="space-y-2 text-sm text-gray-600 dark:text-gray-300">
                <li>✓ DL/ID-2020 Standard</li>
                <li>✓ PDF417 Barcode (30+ fields)</li>
                <li>✓ Ghost Image Security</li>
                <li>✓ Guilloche Patterns</li>
                <li>✓ Microprint Protection</li>
                <li>✓ UV Reactive Features</li>
                <li>✓ Holographic Overlays</li>
              </ul>
            </div>
            <div>
              <h3 className="text-xl font-bold mb-4 text-purple-600 dark:text-purple-400 flex items-center gap-2">
                <Globe className="w-5 h-5" />
                Jurisdiction Coverage
              </h3>
              <ul className="space-y-2 text-sm text-gray-600 dark:text-gray-300">
                <li>✓ All 50 US States + DC + Territories</li>
                <li>✓ Germany, France, Spain, Italy, Netherlands (EU)</li>
                <li>✓ United Kingdom</li>
                <li>✓ Canada (13 Provinces/Territories)</li>
                <li>✓ Australia (8 States/Territories)</li>
                <li>✓ Japan (Color-coded licenses)</li>
                <li>✓ India, Mexico, Brazil (State-specific)</li>
              </ul>
            </div>
            <div>
              <h3 className="text-xl font-bold mb-4 text-green-600 dark:text-green-400 flex items-center gap-2">
                <Zap className="w-5 h-5" />
                Advanced Features
              </h3>
              <ul className="space-y-2 text-sm text-gray-600 dark:text-gray-300">
                <li>✓ Organ Donor Badges</li>
                <li>✓ Veteran Indicators</li>
                <li>✓ Under 21 Auto-Detection</li>
                <li>✓ Magnetic Stripe</li>
                <li>✓ 1D & 2D Barcodes</li>
                <li>✓ Professional Typography</li>
                <li>✓ High-Resolution Output</li>
              </ul>
            </div>
          </div>

          {/* Warning Banner */}
          <div className="bg-gradient-to-r from-yellow-50 to-orange-50 dark:from-yellow-900/20 dark:to-orange-900/20 border-l-4 border-yellow-500 p-5 rounded-r-xl">
            <div className="flex items-start gap-3">
              <AlertCircle className="w-5 h-5 text-yellow-600 dark:text-yellow-400 mt-0.5 flex-shrink-0" />
              <div>
                <h4 className="font-bold text-yellow-800 dark:text-yellow-200 mb-1">Educational & Demonstration Purpose Only</h4>
                <p className="text-sm text-yellow-700 dark:text-yellow-300">
                  This AAMVA-compliant system demonstrates advanced card generation technology. Generated licenses are for educational purposes only and are NOT valid for identification, legal use, or any official purpose.
                </p>
              </div>
            </div>
          </div>
        </footer>

        {/* Powered By */}
        <div className="text-center mt-8 text-sm text-gray-500 dark:text-gray-400">
          <p className="font-semibold mb-2">Powered by Production-Grade Technology Stack</p>
          <div className="flex justify-center gap-4 flex-wrap">
            <span>Java 17 + Spring Boot 3.5</span>
            <span>•</span>
            <span>React 18 + Tailwind CSS</span>
            <span>•</span>
            <span>AAMVA DL/ID-2020</span>
            <span>•</span>
            <span>ZXing Barcode Engine</span>
          </div>
          <p className="mt-4 text-xs opacity-70">
            Demonstrating the pinnacle of AI-assisted software development
          </p>
        </div>
      </div>
    </div>
  );
}

export default App;
