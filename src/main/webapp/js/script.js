$(function(){

	var SN = {

		global: {
			resizeTimer: null
			, sendToItems: 0

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

			, $hr: $('hr')
			, $footer: $('footer')
		}

		, quoteElems: {
			$window: $(window)
			, $sectionHello: $('.section_hello')
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
		}

		, setFullHeight: function() {
			//NOTE: Sorry for this :(
			var windowHeight = SN.quoteElems.$window.height()
				, quoteHeight = SN.quoteElems.$quote.outerHeight() + 60
				, stillWantHeight = SN.quoteElems.$stillWant.outerHeight()
				, sectionHelloHeight = quoteHeight + stillWantHeight
				, firstScreenHeight = (sectionHelloHeight < windowHeight) ? windowHeight : sectionHelloHeight

				, sectionFormHeight = SN.formElems.$sectionForm.outerHeight()
				, hrHeight = SN.global.$hr.outerHeight()
				, footerHeight = SN.global.$footer.outerHeight()
				, secondScreenHeight = sectionFormHeight + hrHeight + footerHeight;

			SN.quoteElems.$sectionHello.css('height', Math.max(firstScreenHeight, 1));
			SN.quoteElems.$stillWant.css('margin-top', Math.max((firstScreenHeight - quoteHeight)/2 - stillWantHeight/2, 0));

			SN.global.$footer.css('padding-bottom', Math.max(windowHeight - 60 - secondScreenHeight, 0));
		}

		, getNewQuote: function() {
			var ajaxInterval;

			function sendRequest() {
				$.ajax({
					url: SN.global.quoteUrl()
					, dataType: 'json'
					, beforeSend: function() {
						clearInterval(ajaxInterval);
					  }
					, success: function(data){

						SN.quoteElems.$quoteText.fadeOut('slow', function() {
							$(this).html(data['advice']);
							$(this).fadeIn('fast', startInterval);
						});
					  }
					, error: function(jqXHR, textStatus, errorThrown){
						log('textStatus: '+ textStatus);
						log('errorThrown: '+ errorThrown);

						startInterval();
					  }
				});
			}

			function startInterval() {
				ajaxInterval = setInterval(sendRequest, SN.global.quoteInterval());
			}

			startInterval();
		}

		, winResize: function() {
			SN.quoteElems.$window.on('resize', function(){
				clearTimeout(SN.global.resizeTimer);
				SN.global.resizeTimer = setTimeout(function(){

					SN.setFullHeight();

				}, 250);
			});
		}

		, bindScrollTo: function() {
			SN.quoteElems.$stillWantLink.on('click', function(e) {
				e.preventDefault();

				SN.quoteElems.$window.scrollTo(SN.formElems.$sectionForm, 1200);
			});
		}

		, getInputTemplate: function(inputType) {
			var inputTemplate
				, inputId
				, inputName
				, inputDataType
				, placeholder;

			switch (inputType) {
				case 'to':
						inputId = 'to-costum';
						inputName = 'to-costum';
						inputDataType = 'to';
						placeholder = '';
					break;
				case 'send-to':
						inputId = 'send-to-%i%';
						inputName = 'send-to-%i%';
						inputDataType = 'send-to';
						placeholder = 'e-mail';
					break;
			}

			inputTemplate = '<div class="input-append">'+
								'<input id="' + inputId + '" class="span3" name="' + inputName + '" type="email" placeholder="' + placeholder + '">'+
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
			$.validator.addMethod('letterswithbasicpunc', function(value, element) {
				return this.optional(element) || /^[a-z-.,()'\"\s]+$/i.test(value);
			}
			, 'Letters with punctuation only please');


			SN.formElems.$form.validate({
				rules: {

					'from': {
						required: true
						, minlength: 4
						, letterswithbasicpunc: true
					  }

					, 'i-want-to-say': {
						required: true
						, minlength: 5
						, letterswithbasicpunc: true
					  }

					, 'send-to': {
						required: true
						, email: true
					  }

					, 'when': {
						required: true,
						date: true
					  }
				  }

				, messages: {

					'from': {
						required: 'Please enter a name and last name'
						, minlength: 'Your username must consist of at least 4 characters'
						, letterswithbasicpunc: 'Letters with punctuation only please'
					}

					, 'i-want-to-say': {
						required: 'Please enter notice'
						, minlength: 'Notice must consist of at least 5 characters'
						, letterswithbasicpunc: 'Letters with punctuation only please'
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

				, submitHandler: function() {

					$.ajax({
						url: SN.global.formSubmitUrl()
						, data: SN.formElems.$form.serialize()
						, success: function(data){
							$('#successModal').modal('show');
						  }
						, error: function(jqXHR, textStatus, errorThrown){
							log('jqXHR: '+ jqXHR);
							log('textStatus: '+ textStatus);
							log('errorThrown: '+ errorThrown);
						  }
					});
				  }
			});
		}

		, initDatePicker: function() {
			SN.formElems.$inputWhen.datetimepicker();
		}

		, init: function() {

			SN.setFullHeight();
			SN.winResize();
			SN.bindScrollTo();
			SN.bindAddCustomInput();
			SN.bindAddSendToInput();
			SN.bindRemoveInput();
			SN.bindSendMyNote();
			SN.getNewQuote();
			SN.initDatePicker();
		}

	};

	SN.init();

});