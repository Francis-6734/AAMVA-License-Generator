export const STATE_FORMATS = {
  CA: { name: 'California', format: 'A1234567', example: 'Letter + 7 digits' },
  NY: { name: 'New York', format: '12345678', example: '8 or 9 digits' },
  TX: { name: 'Texas', format: '12345678', example: '8 digits' },
  FL: { name: 'Florida', format: 'A123-456-78-901-0', example: 'Letter + 12 digits' },
  IL: { name: 'Illinois', format: 'A123-4567-8901', example: 'Letter + 11 digits' },
  PA: { name: 'Pennsylvania', format: '12 345 678', example: '8 digits' },
  OH: { name: 'Ohio', format: 'AB123456', example: '2 letters + 6 digits' },
  GA: { name: 'Georgia', format: '123456789', example: '7-9 digits' },
  NC: { name: 'North Carolina', format: '12345678', example: '1-12 digits' },
  MI: { name: 'Michigan', format: 'A 123 456 789 012', example: 'Letter + 12 digits' },
  NV: { name: 'Nevada', format: '123456789', example: '9-12 digits' },
  WA: { name: 'Washington', format: 'ABCDE12345AB', example: 'Name-based' },
  AZ: { name: 'Arizona', format: 'A12345678', example: 'Letter + 8 digits' },
  MA: { name: 'Massachusetts', format: 'A12345678', example: 'Letter + 8 digits' },
  VA: { name: 'Virginia', format: 'A12345678', example: 'Letter + 8-11 digits' }
};

export const COUNTRIES = {
  USA: { name: 'United States', code: 'US', hasSubJurisdictions: true },
  UK: { name: 'United Kingdom', code: 'GB', hasSubJurisdictions: false },
  CANADA: { name: 'Canada', code: 'CA', hasSubJurisdictions: true },
  GERMANY: { name: 'Germany', code: 'DE', hasSubJurisdictions: false, documentName: 'Führerschein' },
  FRANCE: { name: 'France', code: 'FR', hasSubJurisdictions: false, documentName: 'Permis de Conduire' },
  SPAIN: { name: 'Spain', code: 'ES', hasSubJurisdictions: false, documentName: 'Permiso de Conducción' },
  ITALY: { name: 'Italy', code: 'IT', hasSubJurisdictions: false, documentName: 'Patente di Guida' },
  NETHERLANDS: { name: 'Netherlands', code: 'NL', hasSubJurisdictions: false, documentName: 'Rijbewijs' },
  AUSTRALIA: { name: 'Australia', code: 'AU', hasSubJurisdictions: true },
  JAPAN: { name: 'Japan', code: 'JP', hasSubJurisdictions: false, specialFields: ['colorCode'], documentName: '運転免許証' },
  INDIA: { name: 'India', code: 'IN', hasSubJurisdictions: true },
  MEXICO: { name: 'Mexico', code: 'MX', hasSubJurisdictions: true, documentName: 'Licencia de Conducir' },
  BRAZIL: { name: 'Brazil', code: 'BR', hasSubJurisdictions: true, specialFields: ['nationalId'], documentName: 'CNH' }
};

// Sub-jurisdictions for countries with state/province systems
export const SUB_JURISDICTIONS = {
  AU: [
    { code: 'AU_NSW', name: 'New South Wales' },
    { code: 'AU_VIC', name: 'Victoria' },
    { code: 'AU_QLD', name: 'Queensland' },
    { code: 'AU_WA', name: 'Western Australia' },
    { code: 'AU_SA', name: 'South Australia' },
    { code: 'AU_TAS', name: 'Tasmania' },
    { code: 'AU_ACT', name: 'Australian Capital Territory' },
    { code: 'AU_NT', name: 'Northern Territory' }
  ],
  CA: [
    { code: 'CA_ON', name: 'Ontario' },
    { code: 'CA_BC', name: 'British Columbia' },
    { code: 'CA_AB', name: 'Alberta' },
    { code: 'CA_QC', name: 'Quebec' },
    { code: 'CA_MB', name: 'Manitoba' },
    { code: 'CA_SK', name: 'Saskatchewan' },
    { code: 'CA_NS', name: 'Nova Scotia' },
    { code: 'CA_NB', name: 'New Brunswick' },
    { code: 'CA_NL', name: 'Newfoundland and Labrador' },
    { code: 'CA_PE', name: 'Prince Edward Island' },
    { code: 'CA_YT', name: 'Yukon' },
    { code: 'CA_NT', name: 'Northwest Territories' },
    { code: 'CA_NU', name: 'Nunavut' }
  ],
  IN: [
    { code: 'IN_MH', name: 'Maharashtra' },
    { code: 'IN_DL', name: 'Delhi' },
    { code: 'IN_KA', name: 'Karnataka' },
    { code: 'IN_TN', name: 'Tamil Nadu' },
    { code: 'IN_UP', name: 'Uttar Pradesh' },
    { code: 'IN_GJ', name: 'Gujarat' },
    { code: 'IN_RJ', name: 'Rajasthan' },
    { code: 'IN_WB', name: 'West Bengal' },
    { code: 'IN_AP', name: 'Andhra Pradesh' },
    { code: 'IN_TS', name: 'Telangana' },
    { code: 'IN_KL', name: 'Kerala' },
    { code: 'IN_MP', name: 'Madhya Pradesh' },
    { code: 'IN_PB', name: 'Punjab' },
    { code: 'IN_HR', name: 'Haryana' },
    { code: 'IN_BR', name: 'Bihar' }
  ],
  MX: [
    { code: 'MX_CDMX', name: 'Ciudad de Mexico' },
    { code: 'MX_JAL', name: 'Jalisco' },
    { code: 'MX_NL', name: 'Nuevo Leon' },
    { code: 'MX_BC', name: 'Baja California' },
    { code: 'MX_GTO', name: 'Guanajuato' },
    { code: 'MX_VER', name: 'Veracruz' },
    { code: 'MX_PUE', name: 'Puebla' },
    { code: 'MX_QRO', name: 'Queretaro' },
    { code: 'MX_SON', name: 'Sonora' },
    { code: 'MX_CHIH', name: 'Chihuahua' },
    { code: 'MX_MEX', name: 'Estado de Mexico' },
    { code: 'MX_YUC', name: 'Yucatan' }
  ],
  BR: [
    { code: 'BR_SP', name: 'Sao Paulo' },
    { code: 'BR_RJ', name: 'Rio de Janeiro' },
    { code: 'BR_MG', name: 'Minas Gerais' },
    { code: 'BR_RS', name: 'Rio Grande do Sul' },
    { code: 'BR_PR', name: 'Parana' },
    { code: 'BR_BA', name: 'Bahia' },
    { code: 'BR_SC', name: 'Santa Catarina' },
    { code: 'BR_PE', name: 'Pernambuco' },
    { code: 'BR_CE', name: 'Ceara' },
    { code: 'BR_GO', name: 'Goias' },
    { code: 'BR_DF', name: 'Distrito Federal' },
    { code: 'BR_PA', name: 'Para' }
  ]
};

// EU License Categories
export const EU_LICENSE_CATEGORIES = [
  { code: 'AM', name: 'Mopeds', description: 'Two or three-wheeled vehicles and quadricycles' },
  { code: 'A1', name: 'Light Motorcycles', description: 'Max 125cc, 11kW power' },
  { code: 'A2', name: 'Medium Motorcycles', description: 'Max 35kW power' },
  { code: 'A', name: 'Motorcycles', description: 'No power limit' },
  { code: 'B1', name: 'Light Vehicles', description: 'Tricycles and quadricycles' },
  { code: 'B', name: 'Cars', description: 'Max 3,500kg, max 8 passengers' },
  { code: 'C1', name: 'Medium Trucks', description: '3,500kg to 7,500kg' },
  { code: 'C', name: 'Heavy Trucks', description: 'Over 3,500kg' },
  { code: 'D1', name: 'Minibuses', description: 'Max 16 passengers' },
  { code: 'D', name: 'Buses', description: 'Over 8 passengers' },
  { code: 'BE', name: 'Car + Trailer', description: 'Car with trailer over 750kg' },
  { code: 'C1E', name: 'Medium Truck + Trailer', description: 'C1 with trailer' },
  { code: 'CE', name: 'Heavy Truck + Trailer', description: 'C with trailer' },
  { code: 'D1E', name: 'Minibus + Trailer', description: 'D1 with trailer' },
  { code: 'DE', name: 'Bus + Trailer', description: 'D with trailer' }
];

// Japan License Color Codes
export const JAPAN_LICENSE_COLORS = [
  { code: 'GREEN', name: 'Beginner (Green)', description: 'New drivers (under 3 years)' },
  { code: 'BLUE', name: 'Regular (Blue)', description: 'Standard drivers' },
  { code: 'GOLD', name: 'Excellent (Gold)', description: '5+ years with no violations' }
];

// Check if country is EU member
export const isEUCountry = (countryCode) => {
  const euCountries = ['GERMANY', 'FRANCE', 'SPAIN', 'ITALY', 'NETHERLANDS'];
  return euCountries.includes(countryCode);
};

export const EYE_COLORS = {
  BLK: 'Black',
  BLU: 'Blue',
  BRO: 'Brown',
  GRY: 'Gray',
  GRN: 'Green',
  HAZ: 'Hazel'
};

export const HAIR_COLORS = {
  BLK: 'Black',
  BLN: 'Blonde',
  BRO: 'Brown',
  GRY: 'Gray',
  RED: 'Red',
  WHI: 'White',
  BAL: 'Bald'
};

export const LICENSE_CLASSES = [
  { value: 'C', label: 'Class C - Regular Driver' },
  { value: 'D', label: 'Class D - Commercial' },
  { value: 'M', label: 'Class M - Motorcycle' },
  { value: 'A', label: 'Class A - Heavy Truck' },
  { value: 'B', label: 'Class B - Bus' }
];

// Get sub-jurisdictions for a country
export const getSubJurisdictions = (countryCode) => {
  const country = COUNTRIES[countryCode];
  if (!country || !country.hasSubJurisdictions) return [];
  return SUB_JURISDICTIONS[country.code] || [];
};

// Check if country has special fields
export const getSpecialFields = (countryCode) => {
  const country = COUNTRIES[countryCode];
  return country?.specialFields || [];
};
