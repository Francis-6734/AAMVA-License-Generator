export const downloadImage = (base64Image, filename) => {
  try {
    if (!base64Image) {
      console.error('❌ No image data provided');
      return false;
    }

    // Remove data URL prefix if present
    const base64Data = base64Image.replace(/^data:image\/\w+;base64,/, '');

    const link = document.createElement('a');
    link.href = `data:image/png;base64,${base64Data}`;
    link.download = filename;
    link.style.display = 'none';

    document.body.appendChild(link);
    link.click();

    // Clean up after a short delay
    setTimeout(() => {
      document.body.removeChild(link);
    }, 100);

    console.log(`✅ Downloaded: ${filename}`);
    return true;
  } catch (error) {
    console.error('❌ Download error:', error);
    throw error;
  }
};

export const downloadBothCards = (frontBase64, backBase64, licenseNumber) => {
  try {
    // Download front card
    const frontSuccess = downloadImage(frontBase64, `license_front_${licenseNumber}.png`);

    // Download back card after short delay
    if (frontSuccess && backBase64) {
      setTimeout(() => {
        downloadImage(backBase64, `license_back_${licenseNumber}.png`);
      }, 800);
    }

    return true;
  } catch (error) {
    console.error('❌ Error downloading cards:', error);
    return false;
  }
};