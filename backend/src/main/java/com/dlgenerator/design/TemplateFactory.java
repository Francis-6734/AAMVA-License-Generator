package com.dlgenerator.design;

import com.dlgenerator.design.usa.*;
import com.dlgenerator.design.international.*;
import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.StateFormat;
import com.dlgenerator.model.CountryFormat;
import com.dlgenerator.model.SubJurisdiction;
import org.springframework.stereotype.Component;

@Component
public class TemplateFactory {

    private final CardRenderer renderer;
    private final ImageProcessor imageProcessor;
    private final SecurityFeatureGenerator securityGenerator;

    public TemplateFactory(CardRenderer renderer, ImageProcessor imageProcessor, SecurityFeatureGenerator securityGenerator) {
        this.renderer = renderer;
        this.imageProcessor = imageProcessor;
        this.securityGenerator = securityGenerator;
    }

    public CardTemplate getTemplateForState(StateFormat state) {
        return switch (state) {
            case CA -> new CaliforniaTemplate(renderer, imageProcessor, securityGenerator);
            case NV -> new NevadaTemplate(renderer, imageProcessor, securityGenerator);
            case NY -> new NewYorkTemplate(renderer, imageProcessor, securityGenerator);
            case TX -> new TexasTemplate(renderer, imageProcessor, securityGenerator);
            case FL -> new FloridaTemplate(renderer, imageProcessor, securityGenerator);
            case PA -> new PennsylvaniaTemplate(renderer, imageProcessor, securityGenerator);
            case IL -> new IllinoisTemplate(renderer, imageProcessor, securityGenerator);
            default -> new CaliforniaTemplate(renderer, imageProcessor, securityGenerator);
        };
    }

    /**
     * Get template for country without sub-jurisdiction
     */
    public CardTemplate getTemplateForCountry(CountryFormat country) {
        return getTemplateForCountry(country, null);
    }

    /**
     * Get template for country with optional sub-jurisdiction
     */
    public CardTemplate getTemplateForCountry(CountryFormat country, SubJurisdiction subJurisdiction) {
        return switch (country) {
            // Existing templates
            case UK -> new UKTemplate(renderer, imageProcessor, securityGenerator);
            case CANADA -> new CanadaTemplate(renderer, imageProcessor, securityGenerator);

            // EU Templates
            case GERMANY -> new GermanyTemplate(renderer, imageProcessor, securityGenerator);
            case FRANCE -> new FranceTemplate(renderer, imageProcessor, securityGenerator);
            case SPAIN -> new SpainTemplate(renderer, imageProcessor, securityGenerator);
            case ITALY -> new ItalyTemplate(renderer, imageProcessor, securityGenerator);
            case NETHERLANDS -> new NetherlandsTemplate(renderer, imageProcessor, securityGenerator);

            // Non-EU International Templates (with sub-jurisdiction support)
            case AUSTRALIA -> new AustraliaTemplate(renderer, imageProcessor, securityGenerator, subJurisdiction);
            case JAPAN -> new JapanTemplate(renderer, imageProcessor, securityGenerator);
            case INDIA -> new IndiaTemplate(renderer, imageProcessor, securityGenerator, subJurisdiction);
            case MEXICO -> new MexicoTemplate(renderer, imageProcessor, securityGenerator, subJurisdiction);
            case BRAZIL -> new BrazilTemplate(renderer, imageProcessor, securityGenerator, subJurisdiction);

            // Default to UK template
            default -> new UKTemplate(renderer, imageProcessor, securityGenerator);
        };
    }
}
