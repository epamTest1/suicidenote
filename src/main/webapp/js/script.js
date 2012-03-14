$(function(){

	var SN = {

		global: {
			isMac: /mac/i.test(navigator.userAgent.toLowerCase())
			, resizeTimer: null
			, quoteRequest: null
			, sendToItems: 0
			, $window: $(window)
			, $htmlbody: $('html,body')

			, quoteInterval: function() {
				return SN.quoteElems.$quote.data('interval-value').slice(0, -3) * 1000 || 5000;
			  }
			, quoteUrl: function() {
				return SN.quoteElems.$quote.data('ajax-url');
			  }
			, maxEmails: function() {
				return SN.formElems.$addSendToBtn.data('max-emails') || 10;
			  }
			, formSubmitUrl: function() {
				return SN.formElems.$form.data('ajax-url');
			  }

			, $footer: $('footer')
			, $successModal: $('#successModal')

			, recaptchaPubKey: '6LcEoM4SAAAAAPOVa8ZoB_EPECHZIf2IaMF0Bmae'
		}

		, quoteElems: {
			$sectionHello: $('.section_hello')
			, $quote: $('.quote')
			, $quoteText: $('.quote-text')
			, $stillWant: $('.still-want')
			, $stillWantLink: $('.still-want-link')
		}

		, formElems: {
			$sectionForm: $('.section_form')
			, $form: $('.section_form').find('form')
			, $to: $('#to')
			, $addSendToBtn: $('.icon-plus')
			, $removeBtn: $('.icon-minus')
			, $inputWhen: $('input[type="date"]')
			, $timeZone: $('#time-zone')
			, $recaptchaWrapper: $('#recaptcha-wrapper')
			, $sendMyNote: $('#send-my-note')
		}

		, setFullHeight: function() {
			// first screen (Hello section)
			var windowHeight = SN.global.$window.height()
				, quoteHeight = SN.quoteElems.$quote.outerHeight()
				, stillWantHeight = SN.quoteElems.$stillWant.outerHeight()
				, sectionHelloHeight = quoteHeight + stillWantHeight
				, firstScreenHeight = Math.max(sectionHelloHeight + (60 + 60), windowHeight);

			SN.quoteElems.$sectionHello.css('height', firstScreenHeight);

			var freeHeight4Link = windowHeight - quoteHeight + (60 + 60);

			SN.quoteElems.$stillWant.animate({
				bottom: (freeHeight4Link - stillWantHeight)/2
			}, 'fast');

			// second screen (Form section + hr + footer)
			var formHeight = SN.formElems.$form.outerHeight()
				, hrHeight = 38
				, footerHeight = SN.global.$footer.outerHeight()
				, secondSectionHeight = Math.max(formHeight, windowHeight - (60 + hrHeight + footerHeight));

			SN.formElems.$sectionForm.css('height', secondSectionHeight);

			SN.fixHorizont(firstScreenHeight);
		}

		, fixHorizont: function(firstScreenHeight) {
			if (SN.global.$window.scrollTop() <= firstScreenHeight) {
				SN.scroll2Top();
			} else {
				SN.scroll2Form();
			}
		}

		, sendAjax4NewQuote: function() {
			$.ajax({
				url: SN.global.quoteUrl()
				, dataType: 'json'
				, beforeSend: function() {
					SN.stopQuoteRequest();
				  }
				, success: function(data){

					SN.quoteElems.$quoteText.fadeOut('slow', function() {

						$(this).html(data['text']);
						$(this).fadeIn('fast', SN.startQuoteRequest);
					});
				  }
				, error: function(jqXHR, textStatus, errorThrown){

					log('textStatus: '+ textStatus);
					log('errorThrown: '+ errorThrown);

					SN.startQuoteRequest();
				}
			});
		}

		, startQuoteRequest: function() {
			SN.stopQuoteRequest();
			SN.global.quoteRequest = setInterval(SN.sendAjax4NewQuote, SN.global.quoteInterval());
		}

		, stopQuoteRequest: function() {
			clearInterval(SN.global.quoteRequest);
		}

		, winResize: function() {
			SN.global.$window.on('resize', function(){
				clearTimeout(SN.global.resizeTimer);
				SN.global.resizeTimer = setTimeout(function(){

					SN.setFullHeight();

				}, 250);
			});
		}

		, bindScroll2Form: function() {
			SN.quoteElems.$stillWantLink.on('click', function(e) {
				e.preventDefault();

				SN.scroll2Form();
			});
		}

		, scroll2Top: function() {
			SN.startQuoteRequest();
			SN.global.$htmlbody.animate({scrollTop: 0}, 'slow');
		}

		, scroll2Form: function() {
			SN.stopQuoteRequest();
			SN.global.$htmlbody.animate({scrollTop: $('#section-form').offset().top}, 'slow');
		}

		, getInputTemplate: function(inputVariant) {
			var inputTemplate
				, inputId
				, inputName
				, inputType
				, inputDataType
				, placeholder;

			switch (inputVariant) {
				case 'to':
						inputId = 'to-costum';
						inputName = 'to-costum';
						inputType = 'text';
						inputDataType = 'to';
						placeholder = 'Name, Last name';
					break;
				case 'send-to':
						inputId = 'send-to-%i%';
						inputName = 'send-to-%i%';
						inputType = 'email';
						inputDataType = 'send-to';
						placeholder = 'e-mail';
					break;
			}

			inputTemplate = '<div class="input-append">'+
								'<input id="' + inputId + '" class="span3" name="' + inputName + '" type="' + inputType + '" placeholder="' + placeholder + '">'+
								'<span class="add-on"><i class="icon-minus" data-input-type="' + inputDataType + '"></i></span>'+
							'</div>';

			return inputTemplate;
		}

		, bindAddCustomInput: function() {
			SN.formElems.$to.on('change', function(){

				if ($(this).val() === 'type-name') {

					var inputTemplate = SN.getInputTemplate('to');

					$(this)
						.parent('.input-append')
						.after(inputTemplate);
				} else {

					$('#to-costum')
						.parents('.input-append')
						.remove();
				}
			});

			SN.formElems.$to.trigger('change');
		}

		, bindAddSendToInput: function() {
			var inputTemplate = SN.getInputTemplate('send-to')
				, newInputTemplate = '';

			SN.formElems.$addSendToBtn.on('click', function() {

				if (SN.global.sendToItems === SN.global.maxEmails()) return;

				newInputTemplate = inputTemplate.replace(new RegExp('%i%', 'g'), SN.global.sendToItems);

				$(this)
					.parents('.controls')
					.append(newInputTemplate);

				SN.global.sendToItems++;
			});
		}

		, bindRemoveInput: function() {
			SN.formElems.$removeBtn.live('click', function() {

				$(this)
					.parents('.input-append')
					.remove();

				var inputType = $(this).data('input-type');

				switch (inputType) {
					case 'to':
							SN.formElems.$to.val([0]);
						break;
					case 'send-to':
							SN.global.sendToItems--;
						break;
				}
			});
		}

		, bindSendMyNote: function() {
			$.validator.addMethod('lettersWithBasicPunc', function(value, element) {
				return this.optional(element) || /^[a-z-.,()'"\s]+$/i.test(value);
			}
			, 'Letters with punctuation only please');

			$.validator.addMethod('lettersWithBasicPuncExt', function(value, element) {
				return this.optional(element) || /^[a-z-.,()'"!?:;\s]+$/i.test(value);
			}
			, 'Letters with punctuation only please');

			SN.formElems.$form.validate({
				rules: {

					'from': {
						required: true
						, minlength: 4
						, lettersWithBasicPunc: true
					  }

					, 'to-costum': {
						minlength: 4
						, lettersWithBasicPunc: true
						, required: function() {
							return $('select[name="to"]').val() === 'type-name';
						}
					  }

					, 'i-want-to-say': {
						required: true
						, minlength: 5
						, lettersWithBasicPuncExt: true
					  }

					, 'send-to': {
						required: true
						, email: true
					  }

					, 'when': {
						required: true
						, date: true
					  }
				  }

				, messages: {

					'from': {
						required: 'Please enter a name and last name'
						, minlength: 'Your name must consist of at least 4 characters'
						, lettersWithBasicPunc: 'Letters with punctuation only please'
					}

					, 'to-costum': {
						minlength: 'Name must consist of at least 4 characters'
						, lettersWithBasicPunc: 'Letters with punctuation only please'
					}

					, 'i-want-to-say': {
						required: 'Please enter notice'
						, minlength: 'Notice must consist of at least 5 characters'
						, lettersWithBasicPuncExt: 'Letters with punctuation only please'
					  }

					, 'send-to': 'Please enter a valid email address'

					, 'when': 'Please enter a valid date'
				  }

				, errorPlacement: function(error, element) {
					error.appendTo(element.parent());
				  }

				, highlight: function(element) {
					$(element).parents('div.control-group').addClass('error');
				  }

				, errorElement: 'span'

				, errorClass: 'help-inline'

				, success: function(label) {
					label.parents('.control-group').removeClass('error');
				  }

				, submitHandler: function(form) {
					SN.sendAjax4Submit(form);
				}
			});
		}

		, sendAjax4Submit: function(form) {
			$.ajax({
				url: SN.global.formSubmitUrl()
				, type: 'post'
				, data: $(form).serialize()
				, success: function(data, textStatus, jqXHR) {

					switch (jqXHR.status) {
						case 203: // entered wrong text in captcha
								Recaptcha.reload();
							break;

						case 208: // for second or more attempt, show captcha
								SN.showRecaptcha();
							break;

						default:
							SN.global.$successModal.modal('show');
							SN.global.$successModal.on('hidden', SN.scroll2Top);
							SN.clearForm($(form).find(':input'));

							if (SN.formElems.$recaptchaWrapper.data('recaptcha')) {
								Recaptcha.reload();
							}
					}
				  }
				, error: function(jqXHR, textStatus, errorThrown) {
					log('Text status: '+ textStatus);
					log('Error thrown: '+ errorThrown);
				}
			});
		}

		, showRecaptcha: function() {
			SN.formElems.$recaptchaWrapper
				.data('recaptcha', true)
				.parents('.control-group')
				.removeClass('hidden');

			Recaptcha.create(SN.global.recaptchaPubKey, 'recaptcha-wrapper', {
				theme: 'clean',
				callback: Recaptcha.focus_response_field
			});
		}

		, initDatePicker: function() {
			SN.formElems.$inputWhen.datetimepicker();
		}

		, getTimeZone: function() {
			var currentDate = new Date()
				, currentTimeZoneOffsetInHours = -currentDate.getTimezoneOffset()/60;

			SN.formElems.$timeZone.val(currentTimeZoneOffsetInHours);
		}

		, clearForm: function(form) {
			form.each(function() {
				var type = this.type, tag = this.tagName.toLowerCase();

				if (type == 'text' || type == 'email' || tag == 'textarea' || type == 'date') {
					this.value = '';
				} else if (tag == 'select') {
					this.selectedIndex = 0;
				}
			});
		}

		, keyHandler: function() {
			$(document).bind('keydown', function (e) {

				switch (e.which) {
					case 13: // Enter

							// Submit form by ctrl+enter (command+enter for Mac)
							if ((e.ctrlKey || e.metaKey && SN.global.isMac)) {
								SN.formElems.$sendMyNote.trigger('click');
							}
						break;

					case 9: // Tab
					case 32: // Space
							if ($(e.target).is(':input')) return;

							e.preventDefault();

							if (e.shiftKey) SN.scroll2Top();
						break;

					case 33: // PgUp
					case 38: // Up
					case 34: // PgDown
					case 40: // Down
					case 35: // End
							e.preventDefault();
						break;

					case 36: // Home
					case 27: // Esc
							e.preventDefault();

							SN.scroll2Top();
						break;
				}
			});
		}

		, init: function() {

			SN.setFullHeight();
			SN.winResize();
			SN.bindScroll2Form();
			SN.bindAddCustomInput();
			SN.bindAddSendToInput();
			SN.bindRemoveInput();
			SN.bindSendMyNote();
			//SN.startQuoteRequest();
			SN.initDatePicker();
			SN.getTimeZone();
			SN.keyHandler();
		}

	};

	SN.init();

});